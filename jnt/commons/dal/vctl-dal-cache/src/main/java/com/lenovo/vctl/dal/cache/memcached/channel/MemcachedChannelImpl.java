package com.lenovo.vctl.dal.cache.memcached.channel;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

public class MemcachedChannelImpl implements MemcachedChannel {
    private static Logger log = Logger.getLogger(MemcachedChannelImpl.class);
    private Socket socket;
    private DataInputStream in;
    private BufferedOutputStream out;
    private  boolean health = true;

    public MemcachedChannelImpl(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new BufferedOutputStream(socket.getOutputStream());
    }
    
    

    
    public DataInputStream getIn() {
        return in;
    }




    public void close() throws IOException {
        if (log.isDebugEnabled())
            log.debug("Closing socket for real: " + toString());

        boolean err = false;
        StringBuilder errMsg = new StringBuilder();

        if (in == null || out == null || socket == null) {
            err = true;
            errMsg.append("socket or its streams already null in trueClose call");
            return;
        }

//        if (in != null) {
            try {
                in.close();
            } catch (IOException ioe) {
                log.error("error closing input stream for socket: " + ObjectUtils.toString(socket, ""));
                log.error(ioe.getMessage(), ioe);
                errMsg.append("error closing input stream for socket: ").append(ObjectUtils.toString(socket, ""))
                        .append("\n");
                errMsg.append(ioe.getMessage());
                err = true;
            }
//        }

//        if (out != null) {
            try {
                out.close();
            } catch (IOException ioe) {
                log.error("error closing output stream for socket: " + ObjectUtils.toString(socket, ""));
                log.error(ioe.getMessage(), ioe);
                errMsg.append("error closing output stream for socket: " + ObjectUtils.toString(socket, "") + "\n");
                errMsg.append(ioe.getMessage());
                err = true;
            }
//        }

//        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ioe) {
                log.error("error closing output stream for socket: " + socket.toString());
                log.error(ioe.getMessage(), ioe);
                errMsg.append("error closing output stream for socket: " + socket.toString() + "\n");
                errMsg.append(ioe.getMessage());
                err = true;
            }
//        }

        in = null;
        out = null;
        this.socket = null;

        if (err)
            throw new IOException(errMsg.toString());
    }

    public void flush() throws IOException {
        if (socket == null || !socket.isConnected()) {
            throw new IOException("attempting to write to closed socket");
        }
        out.flush();
    }

    public boolean isAlive() {
        if (!isConnected())
            return false;

        // try to talk to the server w/ a dumb query to ask its version
        try {
            this.write("version\r\n".getBytes());
            this.flush();
//            String response = this.readLine();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean isConnected() {
        return (socket != null && socket.isConnected());
    }

    public void write(byte[] b) throws IOException {
        if (!checkSocket()) {
            log.error("attempting to write to closed socket");
            throw new IOException("attempting to write to closed socket");
        }
        out.write(b);
    }

    public void clearEOL() throws IOException {
        if (!checkSocket()) {
            log.error("attempting to read from closed socket");
            throw new IOException("attempting to read from closed socket");
        }

        byte[] b = new byte[1];
        boolean eol = false;
        while (in.read(b, 0, 1) != -1) {

            // only stop when we see
            // \r (13) followed by \n (10)
            if (b[0] == 13) {
                eol = true;
                continue;
            }

            if (eol) {
                if (b[0] == 10)
                    break;

                eol = false;
            }
        }
    }

    public void clearEOL1() throws IOException {
        if (!checkSocket()) {
            log.error("attempting to read from closed socket");
            throw new IOException("attempting to read from closed socket");
        }

        byte[] b = new byte[1];
        boolean eol = false;
        while (in.read(b, 0, 1) != -1) {

            // only stop when we see
            // \r (13) followed by \n (10)
            // if (b[0] == 13) {
            // eol = true;
            // continue;
            // }
            //
            // if (eol) {
            if (b[0] == 10)
                break;

            // eol = false;
            // }
        }
    }

    public int read(byte[] buf) throws IOException {
        if (!checkSocket()) {
            log.error("attempting to read from closed socket");
            throw new IOException("attempting to read from closed socket");
        }

        int count = 0;
        while (count < buf.length) {
            int cnt = in.read(buf, count, (buf.length - count));
            count += cnt;
        }

        return count;
    }

    public String readLine() throws IOException {
        if (socket == null || !socket.isConnected()) {
            log.error("++++ attempting to read from closed socket");
            throw new IOException("++++ attempting to read from closed socket");
        }

        byte[] b = new byte[1];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        boolean eol = false;

        while (in.read(b, 0, 1) != -1) {

            if (b[0] == 13) {
                eol = true;
            } else {
                if (eol) {
                    if (b[0] == 10)
                        break;

                    eol = false;
                }
            }

            // cast byte into char array
            bos.write(b, 0, 1);
        }

        if (bos == null || bos.size() <= 0) {
            throw new IOException("Stream appears to be dead, so closing it down");
        }

        // else return the string
        return bos.toString().trim();
    }

    public boolean checkSocket() {
        if (socket == null || !socket.isConnected()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("MemcachedChannelImpl[");
        buffer.append("socket = ").append(socket);
        buffer.append("]");
        return buffer.toString();
    }

    @Override
    public void clearEnd() throws IOException {
        if (!checkSocket()) {
            log.error("attempting to read from closed socket");
            throw new IOException("attempting to read from closed socket");
        }

        int len = "END".getBytes().length;
        byte[] keyByte = new byte[len];
        int writeLen = in.read(keyByte, 0, len);

        byte[] b = new byte[1];
        boolean eol = false;
        while (writeLen == len && in.read(b, 0, 1) != -1) {
            // only stop when we see
            // \r (13) followed by \n (10)
            if (b[0] == 13) {
                eol = true;
                continue;
            }

            if (eol) {
                if (b[0] == 10)
                    break;

                eol = false;
            }
        }
    }

    @Override
    public String readKeys(String key) throws IOException {
        if (socket == null || !socket.isConnected()) {
            log.error("++++ attempting to read from closed socket");
            throw new IOException("++++ attempting to read from closed socket");
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int len = key.getBytes().length + "VALUE".getBytes().length;
        byte[] keyByte = new byte[len];
        int writeLen = in.read(keyByte, 0, len);
        if (writeLen != -1) {
            bos.write(keyByte, 0, writeLen);
        }

        byte[] b = new byte[1];

        boolean eol = false;
        if (writeLen == len &&  (keyByte[writeLen-2] != 13 && keyByte[writeLen-1] != 0)) {
            while (in.read(b, 0, 1) != -1) {
                if (b[0] == 13) {
                    eol = true;
                } else {
                    if (eol) {
                        if (b[0] == 10)
                            break;

                        eol = false;
                    }
                }

                // cast byte into char array
                bos.write(b, 0, 1);
            }
        }

        if (bos == null || bos.size() <= 0) {
            throw new IOException("Stream appears to be dead, so closing it down");
        }

        // else return the string
        return bos.toString().trim();
    }




    @Override
    public void setHealth(boolean health) {
        this.health = health;
        
    }


}

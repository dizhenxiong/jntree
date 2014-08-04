package com.lenovo.vctl.dal.cache.memcached.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;
import com.lenovo.vctl.dal.cache.memcached.stream.LineInputStream;

/**
 * 
 * 
 * 提高读的性能。 use get and getMultiArray
 * 
 * @author allenshen date: May 25, 2009 1:23:10 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public class IncrOrDecrInputStreamWrapper implements LineInputStream {
	private DataInputStream is = null;
	private boolean eof = false;
	private int preReadLen = 512; // must > 100;
	private byte[] bytes = new byte[preReadLen];
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private ByteArrayInputStream bis = null;
	private static byte[] END_BYTES = "\r\n".getBytes();
	private static int error_len = "ERROR\r\n".getBytes().length;

	public IncrOrDecrInputStreamWrapper(MemcachedChannel channel)
			throws IOException {
		Long begtime = System.currentTimeMillis();
		if (channel != null) {

			this.is = channel.getIn();
			try {
				while (!eof) {
					if (System.currentTimeMillis() - begtime >= READLONETIME) {
						channel.setHealth(false);
						throw new IOException("read time out: " + READLONETIME);
					}
					readNextByte();
				}
			} catch (IOException e) {
				channel.setHealth(false);
				throw e;
			}
		}
		bis = new ByteArrayInputStream(bos.toByteArray());
	}

	public boolean isEof() {
		return eof;
	}

	/**
	 * 
	 * @throws java.io.IOException
	 */
	private void readNextByte() throws IOException {
		if (is != null && !eof) {
			int iRealLen = is.read(bytes, 0, preReadLen);

			if (iRealLen == preReadLen) {
				eof = checkEof(bytes);
			}

			if (iRealLen != -1) {
				bos.write(bytes, 0, iRealLen);
			}

			if (!eof) {
				byte[] osBytes = bos.toByteArray();
				if (osBytes.length >= END_BYTES.length) {
					eof = checkEof(osBytes);
				}
			}
		}
	}

	private boolean checkEof(byte[] inputByte) {
		boolean eofTemp = true;
		int inputLen = inputByte.length - 1;
		for (int i = END_BYTES.length - 1; i >= 0; i--, inputLen--) {
			byte endByte = END_BYTES[i];
			byte realByte = inputByte[inputLen];
			if (endByte != realByte) {
				eofTemp = false;
				break;
			}
		}
		// 如果没有出错，就看一下是不是有问题了。
		if (!eofTemp && inputByte.length >= error_len) {
			byte[] stats = new byte[7];
			System.arraycopy(inputByte, 0, stats, 0, stats.length);
			String statsStr = new String(stats);
			if (statsStr.indexOf("ERROR") >= 0
					|| statsStr.indexOf("CLIEN") >= 0
					|| statsStr.indexOf("SERVE") >= 0) {
				// "ERROR\r\n" "CLIENT_ERROR <error>\r\n"
				// "SERVER_ERROR <error>\r\n"
				eofTemp = false;
			}
		}
		return eofTemp;
	}

	public String readLine() throws IOException {
		byte[] b = new byte[1];
		ByteArrayOutputStream tmp_bos = new ByteArrayOutputStream();
		boolean eol = false;

		while (bis.read(b, 0, 1) != -1) {
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
			tmp_bos.write(b, 0, 1);
		}

		if (tmp_bos == null || tmp_bos.size() <= 0) {
			throw new IOException(
					"++++ Stream appears to be dead, so closing it down");
		}

		// else return the string
		return tmp_bos.toString().trim();
	}

	@Override
	public void clearEOL() throws IOException {
		byte[] b = new byte[1];
		boolean eol = false;
		while (bis.read(b, 0, 1) != -1) {
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
	public void clearEnd() throws IOException {
		int len = "END".getBytes().length;
		byte[] keyByte = new byte[len];
		int writeLen = bis.read(keyByte, 0, len);

		byte[] b = new byte[1];
		boolean eol = false;
		while (writeLen == len && bis.read(b, 0, 1) != -1) {
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
	public int read(byte[] buf) throws IOException {
		int count = 0;
		while (count < buf.length) {
			int cnt = bis.read(buf, count, (buf.length - count));
			count += cnt;
		}
		return count;
	}

	@Override
	public String readKeys(String key) throws IOException {
		ByteArrayOutputStream temp_bos = new ByteArrayOutputStream();
		int len = key.getBytes().length + "VALUE".getBytes().length;
		byte[] keyByte = new byte[len];
		int writeLen = bis.read(keyByte, 0, len);
		if (writeLen != -1) {
			temp_bos.write(keyByte, 0, writeLen);
		}

		byte[] b = new byte[1];

		boolean eol = false;
		if (writeLen == len
				&& (keyByte[writeLen - 2] != 13 && keyByte[writeLen - 1] != 0)) {
			while (bis.read(b, 0, 1) != -1) {
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
				temp_bos.write(b, 0, 1);
			}
		}

		// else return the string
		String result = temp_bos.toString().trim();

		return result;
	}

}

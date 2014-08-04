package scallop.core.exception;

public class ScallopRemoteException extends Exception {
    private static final long serialVersionUID = -4138189188602563502L;

    public ScallopRemoteException() {
        super();
    }

    public ScallopRemoteException(String message) {
        super(message);
    }

    public ScallopRemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScallopRemoteException(Throwable cause) {
        super(cause);
    }

}

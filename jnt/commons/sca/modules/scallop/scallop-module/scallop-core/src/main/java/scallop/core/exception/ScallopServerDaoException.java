package scallop.core.exception;

public class ScallopServerDaoException extends Exception {
    private static final long serialVersionUID = -4138189188602563502L;

    public ScallopServerDaoException() {
        super();
    }

    public ScallopServerDaoException(String message) {
        super(message);
    }

    public ScallopServerDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScallopServerDaoException(Throwable cause) {
        super(cause);
    }

}

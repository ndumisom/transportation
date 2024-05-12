package za.com.cocamzansi.exception;

public abstract class AbstractBusinessException extends RuntimeException {
    public AbstractBusinessException(final String message) {
        super(message);
    }

    public AbstractBusinessException(final String message, final Throwable t) {
        super(message, t);
    }

    public abstract String getCode();
    public abstract String getDescription();
}

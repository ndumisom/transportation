package za.com.cocamzansi.exception;

public class ResourceForbiddenException extends AbstractBusinessException {
    private String code;
    private String description;

    public ResourceForbiddenException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public ResourceForbiddenException(final String code, final String message, final String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public ResourceForbiddenException(final String code, final String message, final Throwable t) {
        super(message, t);
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

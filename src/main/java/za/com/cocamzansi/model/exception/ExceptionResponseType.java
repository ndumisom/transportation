package za.com.cocamzansi.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public final class ExceptionResponseType {

    @NotNull
    @ApiModelProperty(
            required = true,
            value = "The internal error code. The code can change without notice and should be used for information purpose only")
    private String code;

    @NotNull
    @ApiModelProperty(required = true,
    value = "The detail message of exception")
    private String message;

    @ApiModelProperty("The root cause of the exception")
    private String description;

    public ExceptionResponseType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponseType(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExceptionResponseType{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

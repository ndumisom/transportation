package za.com.cocamzansi.exception;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonInclude(Include.NON_NULL)
@ApiModel
public final class ExceptionResponseType {
    @NotNull
    @ApiModelProperty(
            required = true,
            value = "The internal error code. The code can change without notice and should be used for informational purposes only"
    )
    private String code;
    @NotNull
    @ApiModelProperty(
            required = true,
            value = "The detail message of the exception"
    )
    private String message;
    @ApiModelProperty("The root cause of the exception")
    private String description;

    private ExceptionResponseType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponseType(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ExceptionResponseType valueOf(AbstractBusinessException exception) {
        return new ExceptionResponseType(exception.getCode(), exception.getMessage(), exception.getDescription());
    }

    public String toString() {
        return (new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)).append("code", this.code).append("message", this.message).append("description", this.description).toString();
    }
}

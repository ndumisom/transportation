package za.com.cocamzansi.enumeration;

import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;


@ApiModel
public enum MessageCodeEnum {
	INVALID_MEMBER("RTC-9001"),
	INVALID_NAPPI_CODE("RTC-0002"),
	INVALID_ICD_CODE("RTC-0003"),
	INVALID_PRACTICE_NUMBER("RTC-9002"),
	INVALID_PROVIDER_TYPE("RTC-0005"),
    CLAIM_NOT_FOUND("RTC-0006"),
    DUPLICATE_TRANSACTION("RTC-0007"),
	CLAIM_OUTCOME_NOT_FOUND("RTC-0008"),
	PARTNER_NOT_CONFIGURED("RTC-0009"),
	PARTNER_MIME_TYPE_CONFIG("RTC-0010"),
	OUTCOME_NOT_FOUND("RTC-0011"),
	INVALID_PAYLOAD("RTC-0012"),
	DUPLICATE_CLAIM("RTC-0013"),
	MEMBER_NUMBER_DOES_NOT_MATCH("RTC-0014"),
	PROVIDER_NUMBER_DOES_NOT_MATCH("RTC-0015"),
	TRANSACTION_CLAIM_NUMBER_DOES_NOT_MATCH("RTC-0016"),
	TRANSACTION_TYPE_DOES_NOT_MATCH("RTC-0017"),
	CLAIM_ALREADY_REVERSED("RTC-0018"),
	TIME_OUT_NOT_VALID("RTC-0019"),
	BENEFICIARY_NOT_VALID("RTC-0020"),
	REJECTED_CLAIM_CANNOT_BE_REVERSED("RTC-0021"),
	PENDING_CLAIM_CANNOT_BE_REVERSED("RTC-0022"),
	INVALID_PARAM_VALUE("RTC-0023"),
	_PLATFORM_VALIDATION_ERROR("RTC-0400"),
	INVALID_PARTNER("RTC-0024");

	private String code;

	MessageCodeEnum(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}


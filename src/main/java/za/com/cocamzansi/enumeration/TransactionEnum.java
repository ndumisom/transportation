package za.com.cocamzansi.enumeration;

import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;


@ApiModel
public enum TransactionEnum {

	PENDING("PENDING");

	private String description;

	TransactionEnum(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}


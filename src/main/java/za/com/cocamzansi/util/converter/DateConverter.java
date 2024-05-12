package za.com.cocamzansi.util.converter;

import org.springframework.stereotype.Component;
import za.com.cocamzansi.exception.BadRequestException;
import za.com.cocamzansi.format.ISO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wyatt on 8/3/17.
 */
@Component
public class DateConverter {
    static {
        format = new SimpleDateFormat(ISO.DATE.getFormat());
    }

    private static SimpleDateFormat format;

    public Date fromString(String value) {

        try {
            if (value != null) {
                return format.parse(value);
            }
        } catch (ParseException e) {
            throw new BadRequestException("Error parsing date.",e.getMessage());
        }

        return null;
    }

    public String toString(Date value) {
        // This not mandatory right now
        if (value != null) {
            return format.format(value);
        }
        return null;
    }
}

package za.com.cocamzansi.util.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class COBOLDateDeserializer extends JsonDeserializer<Date> {
    private static final SimpleDateFormat sdfDDMMYYYYSlahed;
    private static final SimpleDateFormat sdfYYYYMMDDNumeric;

    static {
        sdfDDMMYYYYSlahed = new SimpleDateFormat("dd/MM/yyyy");
        sdfYYYYMMDDNumeric = new SimpleDateFormat("yyyyMMdd");
    }

    public COBOLDateDeserializer() {
        super();
    }

    @Override
    public Date deserialize(final JsonParser jsonParser, final DeserializationContext context)
            throws IOException {
        String value = jsonParser.getText().trim();

        if ("".equals(value)) {
            return null;
        }

        try {
            return sdfDDMMYYYYSlahed.parse(jsonParser.getText());
        } catch (ParseException e) {
            // ignore.
        }

        try {
            return sdfYYYYMMDDNumeric.parse(jsonParser.getText());
        } catch (ParseException e) {
            // ignore.
        }

        return null;
    }
}

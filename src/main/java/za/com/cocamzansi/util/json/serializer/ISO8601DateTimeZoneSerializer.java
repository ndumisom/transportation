package za.com.cocamzansi.util.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import za.com.cocamzansi.util.time.DateFormatUtils;

import java.io.IOException;
import java.util.Date;

public final class ISO8601DateTimeZoneSerializer extends JsonSerializer<Date> {
    public ISO8601DateTimeZoneSerializer() {
        super();
    }

    @Override
    public void serialize(final Date value, final JsonGenerator jsonGenerator,
                          final SerializerProvider provider) throws IOException {
        jsonGenerator.writeString(DateFormatUtils.class.toString());
    }
}

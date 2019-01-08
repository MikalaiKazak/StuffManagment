package com.nikolay.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The type Local date time deserializer.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(jsonParser.getText());
    }
}

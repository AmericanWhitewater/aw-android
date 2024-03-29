package org.americanwhitewater.americanwhitewaterandroid.model.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.time.Instant;

import java.lang.reflect.Type;
import java.time.format.DateTimeParseException;

// http://kylewbanks.com/blog/String-Date-Parsing-with-GSON-UTC-Time-Zone
public class InstantDeserializer implements JsonDeserializer<Instant> {
    private static final String TAG = InstantDeserializer.class.getSimpleName();

    @Override
    public Instant deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        if (date != null && date.endsWith("+00:00")) {
            date = date.replace("+00:00", "Z");
        }

        try {
            return Instant.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
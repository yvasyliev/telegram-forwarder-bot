package io.github.yvasyliev.telegramforwarder.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;

/**
 * Deserializer for the "edited" field in Reddit API responses.
 * It returns an {@link Instant} if the value is a number (indicating a timestamp),
 * or null if the value is {@code false} (indicating that the post was not edited).
 */
public class EditedDeserializer extends StdDeserializer<Instant> {
    protected EditedDeserializer() {
        super(Instant.class);
    }

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return JsonToken.VALUE_NUMBER_INT.equals(p.getCurrentToken())
                ? ctxt.readValue(p, Instant.class)
                : null;
    }
}

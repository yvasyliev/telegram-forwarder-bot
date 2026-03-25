package io.github.yvasyliev.forwarder.telegram.reddit.deser;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ext.javatime.deser.InstantDeserializer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for the "edited" field in Reddit API responses. It returns an {@link Instant} if the value is a number
 * (indicating a timestamp), or {@code null} if the value is {@code false} (indicating that the post was not edited).
 */
public class EditedDeserializer extends InstantDeserializer<Instant> {
    public EditedDeserializer() {
        super(InstantDeserializer.INSTANT, DateTimeFormatter.ISO_INSTANT);
    }

    @Override
    protected <R> R _handleUnexpectedToken(DeserializationContext ctxt, JsonParser p, String message, Object... args) {
        return null;
    }
}

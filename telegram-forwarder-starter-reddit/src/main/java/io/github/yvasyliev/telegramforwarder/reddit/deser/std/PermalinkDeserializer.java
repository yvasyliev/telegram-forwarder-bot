package io.github.yvasyliev.telegramforwarder.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Deserializer for Reddit permalink fields.
 * It constructs a full URL by combining the Reddit host with the permalink path.
 */
public class PermalinkDeserializer extends StdDeserializer<URL> {
    private final URI redditHost;

    public PermalinkDeserializer(@Value("${reddit.host}") URI redditHost) {
        super(URL.class);
        this.redditHost = redditHost;
    }

    @Override
    public URL deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return UriComponentsBuilder.fromUri(redditHost)
                .path(p.getText())
                .build()
                .toUri()
                .toURL();
    }
}

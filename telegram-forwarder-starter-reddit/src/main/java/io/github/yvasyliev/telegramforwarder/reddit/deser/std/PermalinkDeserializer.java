package io.github.yvasyliev.telegramforwarder.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;

/**
 * Deserializer for Reddit permalink fields.
 * It constructs a full URL by combining the Reddit host with the permalink path.
 */
public class PermalinkDeserializer extends StdDeserializer<URL> {
    private final RedditProperties redditProperties;

    public PermalinkDeserializer(RedditProperties redditProperties) {
        super(URL.class);
        this.redditProperties = redditProperties;
    }

    @Override
    public URL deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return UriComponentsBuilder.fromUri(redditProperties.host())
                .path(p.getText())
                .build()
                .toUri()
                .toURL();
    }
}

package io.github.yvasyliev.forwarder.telegram.reddit.deser;

import io.github.yvasyliev.forwarder.telegram.reddit.configuration.RedditProperties;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.jdk.JDKFromStringDeserializer;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Deserializer for Reddit permalink fields. It constructs a full URL by combining the Reddit host with the permalink
 * path.
 */
public class PermalinkDeserializer extends JDKFromStringDeserializer {
    private final RedditProperties redditProperties;

    public PermalinkDeserializer(RedditProperties redditProperties) {
        super(URL.class, JDKFromStringDeserializer.STD_URL);
        this.redditProperties = redditProperties;
    }

    @Override
    public Object _deserialize(String path, DeserializationContext ctxt)
            throws JacksonException, MalformedURLException, UnknownHostException {
        return super._deserialize(redditProperties.host() + path, ctxt);
    }
}

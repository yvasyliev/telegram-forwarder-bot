package io.github.yvasyliev.telegramforwarderbot.reddit.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarderbot.configuration.RedditProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

@RequiredArgsConstructor
public class PermalinkDeserializerConverter extends StdConverter<String, URL> {
    private final RedditProperties redditProperties;

    @Override
    public URL convert(String path) {
        try {
            return UriComponentsBuilder.fromUri(redditProperties.host())
                    .path(path)
                    .build()
                    .toUri()
                    .toURL();
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }
}

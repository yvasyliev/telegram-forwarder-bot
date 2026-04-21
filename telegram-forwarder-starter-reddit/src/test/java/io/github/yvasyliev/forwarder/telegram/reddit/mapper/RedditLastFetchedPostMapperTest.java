package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RedditLastFetchedPostMapperTest {
    private static final RedditLastFetchedPostMapper MAPPER = Mappers.getMapper(RedditLastFetchedPostMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapSourceName = () -> {
        var expected = new LastFetchedPostId();

        expected.setSourceType(LastFetchedPostId.SourceType.REDDIT);
        expected.setSourceName("subreddit");

        return Stream.of(arguments(null, null), arguments("subreddit", expected));
    };

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMapStringInstant = () -> {
        var publishedAt = Instant.now();

        var id = new LastFetchedPostId();

        id.setSourceType(LastFetchedPostId.SourceType.REDDIT);
        id.setSourceName("subreddit");

        var expected = new LastFetchedPost();

        expected.setId(id);
        expected.setPublishedAt(publishedAt);

        var expectedWithNullId = new LastFetchedPost();

        expectedWithNullId.setPublishedAt(publishedAt);

        return Stream.of(
                arguments(null, null, null),
                arguments(null, publishedAt, expectedWithNullId),
                arguments("subreddit", publishedAt, expected)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMapSourceName(String sourceName, LastFetchedPostId expected) {
        var actual = MAPPER.map(sourceName);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testMapStringInstant(String id, Instant publishedAt, LastFetchedPost expected) {
        var actual = MAPPER.map(id, publishedAt);

        assertEquals(expected, actual);
    }
}


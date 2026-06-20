package io.github.yvasyliev.forwarder.telegram.x.mapper;

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

class XLastFetchedPostMapperTest {
    private static final XLastFetchedPostMapper MAPPER = Mappers.getMapper(XLastFetchedPostMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var publishedAt1 = Instant.now();

        var id2 = "testId2";
        var publishedAt2 = Instant.now();

        return Stream.of(
                arguments(null, null, null),
                arguments(null, publishedAt1, createLastFetchedPost(null, publishedAt1)),
                arguments(id2, publishedAt2, createLastFetchedPost(createLastFetchedPostId(id2), publishedAt2))
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMap(String id, Instant publishedAt, LastFetchedPost expected) {
        var actual = MAPPER.map(id, publishedAt);

        assertEquals(expected, actual);
    }

    private static LastFetchedPost createLastFetchedPost(LastFetchedPostId id, Instant publishedAt) {
        var lastFetchedPost = new LastFetchedPost();

        lastFetchedPost.setId(id);
        lastFetchedPost.setPublishedAt(publishedAt);

        return lastFetchedPost;
    }

    private static LastFetchedPostId createLastFetchedPostId(String sourceName) {
        var lastFetchedPostId = new LastFetchedPostId();

        lastFetchedPostId.setSourceType(LastFetchedPostId.SourceType.X);
        lastFetchedPostId.setSourceName(sourceName);

        return lastFetchedPostId;
    }
}

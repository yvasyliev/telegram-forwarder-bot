package io.github.yvasyliev.forwarder.telegram.core.service;

import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPost;
import io.github.yvasyliev.forwarder.telegram.core.entity.LastFetchedPostId;
import io.github.yvasyliev.forwarder.telegram.core.repository.LastFetchedPostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LastFetchedPostServiceTest {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testFindPublishedAtById = () -> {
        var now = Instant.now();

        return Stream.of(
                arguments(null, Instant.EPOCH),
                arguments(now, now)
        );
    };

    @InjectMocks private LastFetchedPostService lastFetchedPostService;
    @Mock private LastFetchedPostRepository lastFetchedPostRepository;

    @ParameterizedTest
    @FieldSource
    void testFindPublishedAtById(Instant publishedAt, Instant expected) {
        var id = mock(LastFetchedPostId.class);

        when(lastFetchedPostRepository.findPublishedAtById(id)).thenReturn(Optional.ofNullable(publishedAt));

        var actual = lastFetchedPostService.findPublishedAtById(id);

        assertEquals(expected, actual);
    }

    @Test
    void testSave() {
        var expected = mock(LastFetchedPost.class);

        when(lastFetchedPostRepository.save(expected)).thenReturn(expected);

        var actual = lastFetchedPostService.save(expected);

        assertEquals(expected, actual);
    }
}

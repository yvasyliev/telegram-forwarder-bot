package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.entity.ApprovedPost;
import io.github.yvasyliev.forwarder.telegram.bot.repository.ApprovedPostRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApprovedPostServiceTest {
    @InjectMocks private ApprovedPostService postService;
    @Mock private ApprovedPostRepository postRepository;

    @Test
    void testDelete() {
        var messageIds = List.of(1, 2, 3);
        var post1 = mock(ApprovedPost.class);
        var post2 = mock(ApprovedPost.class);
        var expected = List.of(post1, post2);

        when(postRepository.deleteByMessageIdsIn(messageIds)).thenReturn(expected);

        var actual = postService.delete(messageIds);

        assertEquals(expected, actual);
    }

    @Nested
    class SaveTests {
        private static final List<Integer> MESSAGE_IDS = List.of(1, 2, 3);
        private static final boolean REMOVE_CAPTION = true;
        @SuppressWarnings("checkstyle:Indentation")
        private static final ApprovedPost APPROVED_POST = new ApprovedPost() {{
            setMessageIds(MESSAGE_IDS);
            setRemoveCaption(REMOVE_CAPTION);
        }};
        @SuppressWarnings("checkstyle:Indentation")
        private static final ApprovedPost EXPECTED = new ApprovedPost() {{
            setId(123L);
            setMessageIds(MESSAGE_IDS);
            setRemoveCaption(REMOVE_CAPTION);
            setApprovedAt(Instant.now());
        }};
        @SuppressWarnings("checkstyle:ConstantName")
        private static final Supplier<Stream<ApprovedPost>> testSave = () -> {
            var post = new ApprovedPost();

            post.setMessageIds(MESSAGE_IDS);

            return Stream.of(post, null);
        };

        @ParameterizedTest
        @FieldSource
        void testSave(ApprovedPost post) {
            when(postRepository.findFirstByMessageIdsIn(MESSAGE_IDS)).thenReturn(Optional.ofNullable(post));
            when(postRepository.save(APPROVED_POST)).thenReturn(EXPECTED);

            var actual = postService.save(MESSAGE_IDS, REMOVE_CAPTION);

            assertEquals(EXPECTED, actual);
        }
    }

    @Nested
    class PollTests {
        @Test
        void shouldPollPost() {
            var post = mock(ApprovedPost.class);

            shouldPoll(post);

            verify(postRepository).delete(post);
        }

        @Test
        void shouldReturnEmpty() {
            shouldPoll(null);

            verify(postRepository, never()).delete(any());
        }

        private void shouldPoll(ApprovedPost post) {
            var expected = Optional.ofNullable(post);

            when(postRepository.findFirstByOrderByApprovedAt()).thenReturn(expected);

            var actual = postService.poll();

            assertEquals(expected, actual);
        }
    }
}

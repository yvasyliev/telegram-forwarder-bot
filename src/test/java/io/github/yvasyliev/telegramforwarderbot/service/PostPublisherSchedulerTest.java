package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import io.github.yvasyliev.telegramforwarderbot.entity.ApprovedPost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.CopyMessages;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostPublisherSchedulerTest {
    @InjectMocks private PostPublisherScheduler postPublisherScheduler;
    @Mock private ApprovedPostService postService;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramClient telegramClient;

    @Test
    void shouldDoNothingWhenNoPosts() {
        when(postService.poll()).thenReturn(Optional.empty());

        postPublisherScheduler.publishPost();

        verifyNoInteractions(telegramClient);
    }

    @Nested
    class PublishPostTests {
        private static final String CHANNEL_USERNAME = "@channel";
        private static final long ADMIN_ID = 123456789;
        private static final List<Integer> MESSAGE_IDS = List.of(1, 2, 3);
        private static final boolean REMOVE_CAPTION = true;
        private static final CopyMessages COPY_MESSAGES = CopyMessages.builder()
                .chatId(CHANNEL_USERNAME)
                .fromChatId(ADMIN_ID)
                .messageIds(MESSAGE_IDS)
                .removeCaption(REMOVE_CAPTION)
                .build();

        @BeforeEach
        void setUp() {
            var approvedPost = new ApprovedPost();

            approvedPost.setMessageIds(MESSAGE_IDS);
            approvedPost.setRemoveCaption(REMOVE_CAPTION);

            when(telegramProperties.channelUsername()).thenReturn(CHANNEL_USERNAME);
            when(telegramProperties.adminId()).thenReturn(ADMIN_ID);
            when(postService.poll()).thenReturn(Optional.of(approvedPost));
        }

        @AfterEach
        void tearDown() throws TelegramApiException {
            verify(telegramClient).execute(COPY_MESSAGES);
        }

        @Test
        void shouldCopyMessages() {
            postPublisherScheduler.publishPost();
        }

        @Test
        void shouldHandleException() throws TelegramApiException {
            when(telegramClient.execute(COPY_MESSAGES)).thenThrow(TelegramApiException.class);

            assertDoesNotThrow(postPublisherScheduler::publishPost);
        }
    }
}
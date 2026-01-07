package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.TelegramChannelProperties;
import io.github.yvasyliev.forwarder.telegram.bot.entity.ApprovedPost;
import io.github.yvasyliev.forwarder.telegram.bot.mapper.CopyMessagesMapper;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostPublisherSchedulerTest {
    @InjectMocks private PostPublisherScheduler postPublisherScheduler;
    @Mock private TelegramChannelProperties channelProperties;
    @Mock private TelegramAdminProperties adminProperties;
    @Mock private ApprovedPostService postService;
    @Mock private CopyMessagesMapper copyMessagesMapper;
    @Mock private TelegramClient telegramClient;

    @Test
    void shouldNotPublishPost() {
        when(postService.poll()).thenReturn(Optional.empty());

        postPublisherScheduler.publishPost();

        verifyNoInteractions(telegramClient);
    }

    @Nested
    class PublishPostTest {
        @Mock private ApprovedPost approvedPost;
        @Mock private CopyMessages copyMessages;

        @BeforeEach
        void setUp() {
            when(postService.poll()).thenReturn(Optional.of(approvedPost));
            when(copyMessagesMapper.map(channelProperties, adminProperties, approvedPost)).thenReturn(copyMessages);
        }

        @AfterEach
        void tearDown() throws TelegramApiException {
            verify(telegramClient).execute(copyMessages);
        }

        @Test
        void shouldPublishPost() {
            postPublisherScheduler.publishPost();
        }

        @Test
        void shouldHandleTelegramApiException() throws TelegramApiException {
            when(telegramClient.execute(copyMessages)).thenThrow(TelegramApiException.class);

            assertDoesNotThrow(() -> postPublisherScheduler.publishPost());
        }
    }
}

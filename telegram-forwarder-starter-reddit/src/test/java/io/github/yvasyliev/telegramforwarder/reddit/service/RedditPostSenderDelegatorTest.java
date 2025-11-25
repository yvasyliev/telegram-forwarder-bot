package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSender;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.RedditPostSenderResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditPostSenderDelegatorTest {
    private static final Instant CREATED_AT = Instant.now();
    @SuppressWarnings("checkstyle:ConstantName") private static final Exception[] shouldHandleException = {
            new IOException("IO error"),
            new TelegramApiException("Telegram API error")
    };
    @InjectMocks private RedditPostSenderDelegator redditPostSenderDelegator;
    @Mock private RedditPostSenderResolver redditPostSenderResolver;
    @Mock private RedditInstantPropertyService instantPropertyService;
    @Mock private RedditPostSender redditPostSender;
    @Mock private Link post;

    @BeforeEach
    void setUp() {
        when(redditPostSenderResolver.resolve(post)).thenReturn(redditPostSender);
        when(post.created()).thenReturn(CREATED_AT);
    }

    @AfterEach
    void tearDown() throws TelegramApiException, IOException {
        verify(redditPostSender).send(post);
        verify(instantPropertyService).saveLastCreated(CREATED_AT);
    }

    @Test
    void shouldSendPost() {
        redditPostSenderDelegator.send(post);
    }

    @ParameterizedTest
    @FieldSource
    void shouldHandleException(Exception e) throws TelegramApiException, IOException {
        doThrow(e).when(redditPostSender).send(post);

        assertDoesNotThrow(() -> redditPostSenderDelegator.send(post));
    }
}

package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.service.sender.strategy.RedditPostSenderStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditPostSenderManagerTest {
    private static final Instant NOW = Instant.now();
    @Mock private RedditPostSenderStrategy postSenderStrategy;
    @Mock private RedditInstantPropertyService instantPropertyService;
    @Mock private Link post;
    private RedditPostSenderManager postSenderManager;

    @BeforeEach
    void setUp() {
        postSenderManager = new RedditPostSenderManager(List.of(postSenderStrategy), instantPropertyService);

        when(post.created()).thenReturn(NOW);
    }

    @AfterEach
    void tearDown() {
        verify(instantPropertyService).saveLastCreated(NOW);
    }

    @Test
    void shouldSendPost() throws TelegramApiException, IOException {
        when(postSenderStrategy.canSend(post)).thenReturn(true);

        postSenderManager.send(post);

        verify(postSenderStrategy).send(post);
    }

    @Test
    void shouldHandleUnknownPostType() {
        assertDoesNotThrow(() -> postSenderManager.send(post));
    }

    @ParameterizedTest
    @ValueSource(classes = {IOException.class, TelegramApiException.class})
    void shouldHandleException(Class<Exception> eClass) throws TelegramApiException, IOException {
        when(postSenderStrategy.canSend(post)).thenReturn(true);
        doThrow(eClass).when(postSenderStrategy).send(post);

        assertDoesNotThrow(() -> postSenderManager.send(post));
    }
}

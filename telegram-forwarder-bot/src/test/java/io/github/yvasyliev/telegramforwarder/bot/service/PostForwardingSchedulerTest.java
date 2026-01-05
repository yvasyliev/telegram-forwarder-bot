package io.github.yvasyliev.telegramforwarder.bot.service;

import io.github.yvasyliev.telegramforwarder.core.service.PostForwarder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostForwardingSchedulerTest {
    @Mock private PostForwarder postForwarder;
    private PostForwardingScheduler postForwardingScheduler;

    @BeforeEach
    void setUp() {
        postForwardingScheduler = new PostForwardingScheduler(List.of(postForwarder));
    }

    @AfterEach
    void tearDown() {
        verify(postForwarder).forward();
    }

    @Test
    void shouldForwardPosts() {
        postForwardingScheduler.forwardPosts();
    }

    @Test
    void shouldHandleExceptionDuringForwarding() {
        doThrow(RuntimeException.class).when(postForwarder).forward();

        assertDoesNotThrow(postForwardingScheduler::forwardPosts);
    }
}

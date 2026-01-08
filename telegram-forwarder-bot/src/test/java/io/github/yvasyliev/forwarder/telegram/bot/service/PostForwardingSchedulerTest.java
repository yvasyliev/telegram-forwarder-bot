package io.github.yvasyliev.forwarder.telegram.bot.service;

import io.github.yvasyliev.forwarder.telegram.core.service.PostForwarder;
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

    @Test
    void shouldForwardPosts() {
        testForwardPosts();
    }

    @Test
    void shouldHandleExceptionDuringForwarding() {
        doThrow(RuntimeException.class).when(postForwarder).forward();

        testForwardPosts();
    }

    private void testForwardPosts() {
        assertDoesNotThrow(postForwardingScheduler::forwardPosts);

        verify(postForwarder).forward();
    }
}

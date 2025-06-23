package io.github.yvasyliev.telegramforwarderbot.service;

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
class PostForwardSchedulerTest {
    @Mock private PostForwarderManager postForwarderManager;
    private PostForwardScheduler postForwardScheduler;

    @BeforeEach
    void setUp() {
        postForwardScheduler = new PostForwardScheduler(List.of(postForwarderManager));
    }

    @AfterEach
    void tearDown() {
        verify(postForwarderManager).forward();
    }

    @Test
    void shouldForwardPosts() {
        postForwardScheduler.forwardPosts();
    }

    @Test
    void shouldHandleExceptionDuringForwarding() {
        doThrow(RuntimeException.class).when(postForwarderManager).forward();

        assertDoesNotThrow(postForwardScheduler::forwardPosts);
    }
}

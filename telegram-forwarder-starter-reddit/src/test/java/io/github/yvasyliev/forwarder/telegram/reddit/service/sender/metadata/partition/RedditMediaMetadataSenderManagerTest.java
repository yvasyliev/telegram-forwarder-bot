package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.RedditMediaMetadataSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditMediaMetadataSenderManagerTest {
    @Mock private RedditMediaMetadataSender animationMetadataSender;
    @Mock private RedditMediaMetadataSender photoMetadataSender;
    private RedditMetadataPartitionSender redditMediaMetadataSenderManager;

    @BeforeEach
    void setUp() {
        redditMediaMetadataSenderManager = new RedditMediaMetadataSenderManager(
                animationMetadataSender,
                photoMetadataSender
        );
    }

    @Test
    void shouldSendAnimatedImage() throws TelegramApiException, IOException {
        testSend(Link.Metadata.Type.ANIMATED_IMAGE, animationMetadataSender);
    }

    @Test
    void shouldSendImage() throws TelegramApiException, IOException {
        testSend(Link.Metadata.Type.IMAGE, photoMetadataSender);
    }

    private void testSend(Link.Metadata.Type type, RedditMediaMetadataSender redditMediaMetadataSender)
            throws TelegramApiException, IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var caption = "caption";

        when(metadata.type()).thenReturn(type);

        assertDoesNotThrow(() -> redditMediaMetadataSenderManager.send(List.of(metadata), hasSpoiler, caption));

        verify(redditMediaMetadataSender).send(metadata, hasSpoiler);
    }
}

package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
class SingleItemMetadataSenderStrategyTest {
    @InjectMocks private SingleItemMetadataSenderStrategy singleItemMetadataSenderStrategy;
    @Mock private RedditMetadataSenderResolver redditMetadataSenderResolver;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var metadata = mock(Link.Metadata.class);
        var hasSpoiler = true;
        var redditMetadataSender = mock(RedditMetadataSender.class);

        when(redditMetadataSenderResolver.resolve(metadata)).thenReturn(redditMetadataSender);

        assertDoesNotThrow(() -> singleItemMetadataSenderStrategy.send(List.of(metadata), hasSpoiler, null));

        verify(redditMetadataSender).send(metadata, hasSpoiler);
    }
}

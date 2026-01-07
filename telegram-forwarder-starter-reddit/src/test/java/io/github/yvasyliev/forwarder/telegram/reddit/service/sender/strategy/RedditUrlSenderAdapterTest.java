package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.RedditPostSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditUrlSenderAdapterTest {
    @InjectMocks private RedditUrlSenderAdapter urlSenderAdapter;
    @Mock private RedditPostSender urlSender;

    @ParameterizedTest
    @CsvSource(
            textBlock = """
                        HOSTED_VIDEO, false
                        IMAGE, false
                        LINK, true
                        RICH_VIDEO, false
                        GALLERY, false
                        , false"""
    )
    void testCanSend(Link.PostHint postHint, boolean expected) {
        var post = mock(Link.class);

        when(post.postHint()).thenReturn(postHint);

        var actual = urlSenderAdapter.canSend(post);

        assertEquals(expected, actual);
    }

    @Test
    void testSend() throws TelegramApiException, IOException {
        var post = mock(Link.class);

        assertDoesNotThrow(() -> urlSenderAdapter.send(post));

        verify(urlSender).send(post);
    }
}

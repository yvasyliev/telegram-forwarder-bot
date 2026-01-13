package io.github.yvasyliev.forwarder.telegram.reddit.service;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditPostForwarderTest {
    @InjectMocks private RedditPostForwarder postForwarder;
    @Mock private RedditLinkService linkService;
    @Mock private RedditPostSenderManager resolvingPostSender;

    @AfterEach
    void tearDown() {
        verify(linkService).getNewLinks();
    }

    @Test
    void shouldForwardNewLinks() {
        var post = mock(Link.class);

        when(linkService.getNewLinks()).thenReturn(Stream.of(post));

        postForwarder.forward();

        verify(resolvingPostSender).send(post);
    }

    @Test
    void shouldDoNothingWhenNoNewLinks() {
        when(linkService.getNewLinks()).thenReturn(Stream.empty());

        postForwarder.forward();

        verifyNoInteractions(resolvingPostSender);
    }
}

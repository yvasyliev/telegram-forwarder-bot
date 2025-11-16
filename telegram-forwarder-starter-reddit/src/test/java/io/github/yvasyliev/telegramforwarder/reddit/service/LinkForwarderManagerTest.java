package io.github.yvasyliev.telegramforwarder.reddit.service;

import io.github.yvasyliev.telegramforwarder.reddit.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarder.reddit.dto.Thing;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkForwarderManagerTest {
    private static final String SUBREDDIT = "testSubreddit";
    private static final Instant CREATED = Instant.now();
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Exception[] shouldCatchCheckedException = {new IOException(), new TelegramApiException()};
    @InjectMocks private LinkForwarderManager forwarderManager;
    @Mock private RedditInstantPropertyService propertyService;
    @Mock private RedditService redditService;
    @Mock private RedditProperties redditProperties;
    @Mock private LinkForwarderFactory forwarderFactory;
    @Mock private Thing<Listing> subredditNew;
    @Mock private Thing<Link> child;
    @Mock private Link link;
    @Mock private LinkForwarder forwarder;

    @BeforeEach
    void setUp() {
        var listing = mock(Listing.class);

        when(propertyService.getLastCreated()).thenReturn(Instant.EPOCH);
        when(redditProperties.subreddit()).thenReturn(SUBREDDIT);
        when(redditService.getSubredditNew(SUBREDDIT)).thenReturn(subredditNew);
        when(subredditNew.data()).thenReturn(listing);
        when(listing.children()).thenReturn(List.of(child));
        when(child.data()).thenReturn(link);
        when(link.created()).thenReturn(CREATED);
        when(link.sourceLink()).thenReturn(link);
        when(forwarderFactory.apply(link)).thenReturn(forwarder);
    }

    @AfterEach
    void tearDown() throws TelegramApiException, IOException {
        verify(forwarder).forward(link);
        verify(propertyService).saveLastCreated(CREATED);
    }

    @Test
    void shouldForwardLink() {
        forwarderManager.run();
    }

    @ParameterizedTest
    @FieldSource
    void shouldCatchCheckedException(Exception e) throws TelegramApiException, IOException {
        doThrow(e).when(forwarder).forward(link);

        assertDoesNotThrow(forwarderManager::run);
    }
}

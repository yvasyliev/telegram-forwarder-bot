package io.github.yvasyliev.telegramforwarder.bot.reddit.service;

import io.github.yvasyliev.telegramforwarder.bot.configuration.RedditProperties;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Listing;
import io.github.yvasyliev.telegramforwarder.bot.reddit.dto.Thing;
import io.github.yvasyliev.telegramforwarder.bot.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditPostForwarderManagerTest {
    private static final String SUBREDDIT = "testSubreddit";
    private static final Instant CREATED = Instant.now();
    @SuppressWarnings("checkstyle:ConstantName")
    private static final Exception[] shouldCatchCheckedException = {new IOException(), new TelegramApiException()};
    private PostForwarderManager forwarderManager;
    @Mock private RedditInstantPropertyService propertyService;
    @Mock private RedditService redditService;
    @Mock private RedditProperties redditProperties;
    @Mock private Forwarder mediaGroupForwarder;
    @Mock private Forwarder videoForwarder;
    @Mock private Forwarder imageAnimationForwarder;
    @Mock private Forwarder photoForwarder;
    @Mock private Forwarder linkForwarder;
    @Mock private Forwarder videoAnimationForwarder;
    @Mock private Thing<Listing> subredditNew;
    @Mock private Thing<Link> child;
    @Mock private Link link;

    @BeforeEach
    void setUp() {
        forwarderManager = new RedditPostForwarderManager(
                propertyService,
                redditService,
                redditProperties,
                mediaGroupForwarder,
                videoForwarder,
                imageAnimationForwarder,
                photoForwarder,
                linkForwarder,
                videoAnimationForwarder
        );
        var listing = mock(Listing.class);

        when(propertyService.getLastCreated()).thenReturn(Instant.EPOCH);
        when(redditProperties.subreddit()).thenReturn(SUBREDDIT);
        when(redditService.getSubredditNew(SUBREDDIT)).thenReturn(subredditNew);
        when(subredditNew.data()).thenReturn(listing);
        when(listing.children()).thenReturn(List.of(child));
        when(child.data()).thenReturn(link);
        when(link.created()).thenReturn(CREATED);
    }

    @AfterEach
    void tearDown() {
        verify(propertyService).saveLastCreated(CREATED);
    }

    @Test
    void shouldForwardCrosspost() throws TelegramApiException, IOException {
        var parentLink = mock(Link.class);

        when(link.crosspostParentList()).thenReturn(List.of(parentLink));
        when(parentLink.hasGalleryData()).thenReturn(true);

        forwarderManager.forward();

        verify(mediaGroupForwarder).forward(parentLink);
    }

    @Test
    void shouldForwardMediaGroup() throws TelegramApiException, IOException {
        when(link.hasGalleryData()).thenReturn(true);

        forwarderManager.forward();

        verify(mediaGroupForwarder).forward(link);
    }

    @Test
    void shouldForwardVideo() throws TelegramApiException, IOException {
        when(link.hasPostHint()).thenReturn(true);
        when(link.postHint()).thenReturn(Link.PostHint.HOSTED_VIDEO);

        forwarderManager.forward();

        verify(videoForwarder).forward(link);
    }

    @Test
    void shouldForwardImageAnimation() throws TelegramApiException, IOException {
        shouldForwardImage(true, imageAnimationForwarder);
    }

    @Test
    void shouldForwardPhoto() throws TelegramApiException, IOException {
        shouldForwardImage(false, photoForwarder);
    }

    @Test
    void shouldForwardLink() throws TelegramApiException, IOException {
        when(link.hasPostHint()).thenReturn(true);
        when(link.postHint()).thenReturn(Link.PostHint.LINK);

        forwarderManager.forward();

        verify(linkForwarder).forward(link);
    }

    @Test
    void shouldForwardRichVideoGif() throws TelegramApiException, IOException {
        shouldForwardRichVideo(mock(Link.RedditVideo.class), true, videoAnimationForwarder);
    }

    @Test
    void shouldForwardRichVideoNonGif() throws TelegramApiException, IOException {
        shouldForwardRichVideo(mock(Link.RedditVideo.class), false, linkForwarder);
    }

    @Test
    void shouldForwardRichVideoNull() throws TelegramApiException, IOException {
        var preview = mock(Link.Preview.class);

        when(link.hasPostHint()).thenReturn(true);
        when(link.postHint()).thenReturn(Link.PostHint.RICH_VIDEO);
        when(link.preview()).thenReturn(preview);

        forwarderManager.forward();

        verify(linkForwarder).forward(link);
    }

    @ParameterizedTest
    @FieldSource
    void shouldCatchCheckedException(Exception e) throws TelegramApiException, IOException {
        when(link.hasGalleryData()).thenReturn(true);
        doThrow(e).when(mediaGroupForwarder).forward(link);

        assertDoesNotThrow(forwarderManager::forward);

        verify(mediaGroupForwarder).forward(link);
    }

    private void shouldForwardImage(boolean hasGif, Forwarder forwarder) throws TelegramApiException, IOException {
        var preview = mock(Link.Preview.class);
        var image = mock(Link.Preview.Image.class);
        var variants = mock(Link.Variants.class);

        when(link.hasPostHint()).thenReturn(true);
        when(link.postHint()).thenReturn(Link.PostHint.IMAGE);
        when(link.preview()).thenReturn(preview);
        when(preview.images()).thenReturn(List.of(image));
        when(image.variants()).thenReturn(variants);
        when(variants.hasGif()).thenReturn(hasGif);

        forwarderManager.forward();

        verify(forwarder).forward(link);
    }

    private void shouldForwardRichVideo(
            Link.RedditVideo redditVideo,
            boolean isGif,
            Forwarder forwarder
    ) throws TelegramApiException, IOException {
        var preview = mock(Link.Preview.class);

        when(link.hasPostHint()).thenReturn(true);
        when(link.postHint()).thenReturn(Link.PostHint.RICH_VIDEO);
        when(link.preview()).thenReturn(preview);
        when(preview.redditVideoPreview()).thenReturn(redditVideo);
        when(redditVideo.isGif()).thenReturn(isGif);

        forwarderManager.forward();

        verify(forwarder).forward(link);
    }

    @Nested
    class ShouldNotForwardTests {
        @AfterEach
        void tearDown() {
            verifyNoInteractions(
                    mediaGroupForwarder,
                    videoForwarder,
                    imageAnimationForwarder,
                    photoForwarder,
                    linkForwarder,
                    videoAnimationForwarder
            );
        }

        @Test
        void testWhenNoForwarderFound() {
            forwarderManager.forward();
        }

        @Test
        void testWhenUnhandledPostHint() {
            when(link.hasPostHint()).thenReturn(true);
            when(link.postHint()).thenReturn(Link.PostHint.GALLERY);

            forwarderManager.forward();
        }
    }
}

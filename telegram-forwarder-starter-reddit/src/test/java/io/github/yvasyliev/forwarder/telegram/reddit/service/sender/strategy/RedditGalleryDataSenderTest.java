package io.github.yvasyliev.forwarder.telegram.reddit.service.sender.strategy;

import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import io.github.yvasyliev.forwarder.telegram.reddit.service.sender.metadata.partition.RedditMetadataPartitionSender;
import io.github.yvasyliev.forwarder.telegram.reddit.util.GalleryMetadataPartitioner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedditGalleryDataSenderTest {
    @Mock private GalleryMetadataPartitioner galleryMetadataPartitioner;
    @Mock private RedditMetadataPartitionSender mediaMetadataSender;
    @Mock private RedditMetadataPartitionSender mediaGroupMetadataSender;
    private RedditPostSenderStrategy galleryDataSender;

    @BeforeEach
    void setUp() {
        galleryDataSender = new RedditGalleryDataSender(
                galleryMetadataPartitioner,
                mediaMetadataSender,
                mediaGroupMetadataSender
        );
    }

    @ParameterizedTest
    @CsvSource({"true, true", "false, false"})
    void testCanSend(boolean hasGalleryData, boolean expected) {
        var post = mock(Link.class);

        when(post.hasGalleryData()).thenReturn(hasGalleryData);

        var actual = galleryDataSender.canSend(post);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSendOnePost() throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var caption = "Sample Caption";
        var hasSpoiler = true;
        var metadataPartition = List.of(mock(Link.Metadata.class), mock(Link.Metadata.class));

        when(post.title()).thenReturn(caption);
        when(post.isNsfw()).thenReturn(hasSpoiler);
        when(galleryMetadataPartitioner.partition(post)).thenReturn(List.of(metadataPartition));

        assertDoesNotThrow(() -> galleryDataSender.send(post));

        verify(mediaGroupMetadataSender).send(metadataPartition, hasSpoiler, caption);
    }

    @Test
    void shouldSendTwoPosts() throws TelegramApiException, IOException {
        var post = mock(Link.class);
        var caption = "Sample Caption";
        var hasSpoiler = false;
        var firstPartition = List.of(mock(Link.Metadata.class), mock(Link.Metadata.class));
        var secondPartition = List.of(mock(Link.Metadata.class));

        when(post.title()).thenReturn(caption);
        when(post.isNsfw()).thenReturn(hasSpoiler);
        when(galleryMetadataPartitioner.partition(post)).thenReturn(List.of(firstPartition, secondPartition));

        assertDoesNotThrow(() -> galleryDataSender.send(post));

        verify(mediaGroupMetadataSender).send(firstPartition, hasSpoiler, caption);
        verify(mediaMetadataSender).send(secondPartition, hasSpoiler, null);
    }
}

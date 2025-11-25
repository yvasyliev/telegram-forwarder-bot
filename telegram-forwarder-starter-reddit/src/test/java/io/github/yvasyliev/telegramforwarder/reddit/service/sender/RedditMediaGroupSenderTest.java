package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarder.reddit.util.GalleryMetadataPartitioner;
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
class RedditMediaGroupSenderTest {
    @InjectMocks private RedditMediaGroupSender redditMediaGroupSender;
    @Mock private GalleryMetadataPartitioner galleryMetadataPartitioner;
    @Mock private MetadataPartitionSenderStrategyResolver metadataPartitionSenderStrategyResolver;

    @Test
    void testSend() throws TelegramApiException, IOException {
        var hasSpoiler = true;
        var caption = "Test Caption";
        var post = mock(Link.class);
        var metadataPartition1 = List.of(mock(Link.Metadata.class));
        var metadataPartition2 = List.of(mock(Link.Metadata.class));
        var metadataPartitionSenderStrategy = mock(MetadataPartitionSenderStrategy.class);

        when(post.isNsfw()).thenReturn(hasSpoiler);
        when(post.title()).thenReturn(caption);
        when(galleryMetadataPartitioner.partition(post)).thenReturn(List.of(metadataPartition1, metadataPartition2));
        when(metadataPartitionSenderStrategyResolver.resolve(metadataPartition1))
                .thenReturn(metadataPartitionSenderStrategy);
        when(metadataPartitionSenderStrategyResolver.resolve(metadataPartition2))
                .thenReturn(metadataPartitionSenderStrategy);

        assertDoesNotThrow(() -> redditMediaGroupSender.send(post));

        verify(metadataPartitionSenderStrategy).send(metadataPartition1, hasSpoiler, caption);
        verify(metadataPartitionSenderStrategy).send(metadataPartition2, hasSpoiler, null);
    }
}

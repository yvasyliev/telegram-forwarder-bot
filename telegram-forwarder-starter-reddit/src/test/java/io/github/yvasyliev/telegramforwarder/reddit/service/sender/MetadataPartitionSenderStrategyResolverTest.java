package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MetadataPartitionSenderStrategyResolverTest {
    private MetadataPartitionSenderStrategyResolver metadataPartitionSenderStrategyResolver;
    @Mock private MetadataPartitionSenderStrategy singleItemMetadataSenderStrategy;
    @Mock private MetadataPartitionSenderStrategy mediaGroupMetadataSenderStrategy;

    @BeforeEach
    void setUp() {
        metadataPartitionSenderStrategyResolver = new MetadataPartitionSenderStrategyResolver(
                singleItemMetadataSenderStrategy,
                mediaGroupMetadataSenderStrategy
        );
    }

    @Test
    void shouldReturnSingleItemMetadataSenderStrategy() {
        testResolve(List.of(mock(Link.Metadata.class)), singleItemMetadataSenderStrategy);
    }

    @Test
    void shouldReturnMediaGroupMetadataSenderStrategy() {
        testResolve(List.of(mock(Link.Metadata.class), mock(Link.Metadata.class)), mediaGroupMetadataSenderStrategy);
    }

    private void testResolve(List<Link.Metadata> metadataPartition, MetadataPartitionSenderStrategy expected) {
        var actual = metadataPartitionSenderStrategyResolver.resolve(metadataPartition);

        assertEquals(expected, actual);
    }
}

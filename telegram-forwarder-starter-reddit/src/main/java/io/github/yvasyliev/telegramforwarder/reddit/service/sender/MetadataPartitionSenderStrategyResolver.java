package io.github.yvasyliev.telegramforwarder.reddit.service.sender;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

/**
 * Resolves the appropriate {@link MetadataPartitionSenderStrategy} based on the size of the metadata partition.
 */
@RequiredArgsConstructor
public class MetadataPartitionSenderStrategyResolver {
    private final MetadataPartitionSenderStrategy singleItemMetadataSenderStrategy;
    private final MetadataPartitionSenderStrategy mediaGroupMetadataSenderStrategy;

    /**
     * Resolves the appropriate strategy based on the size of the metadata partition.
     *
     * @param metadataPartition the list of metadata items
     * @return the resolved {@link MetadataPartitionSenderStrategy}
     */
    public MetadataPartitionSenderStrategy resolve(List<Link.Metadata> metadataPartition) {
        return NumberUtils.INTEGER_ONE.equals(metadataPartition.size())
                ? singleItemMetadataSenderStrategy
                : mediaGroupMetadataSenderStrategy;
    }
}

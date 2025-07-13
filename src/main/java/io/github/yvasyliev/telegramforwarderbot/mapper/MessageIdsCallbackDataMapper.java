package io.github.yvasyliev.telegramforwarderbot.mapper;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Mapper interface for converting between {@code MessageIdsCommandCallbackDataDTO} and {@code
 * MessageIdsTelegramCommandCallbackDataDTO}.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper
public interface MessageIdsCallbackDataMapper {
    /**
     * Maps a {@code MessageIdsCommandCallbackDataDTO} to a {@code MessageIdsTelegramCommandCallbackDataDTO}.
     *
     * @param callbackData the source callback data
     * @return the mapped Telegram command callback data
     */
    @Mapping(target = "firstMessageId", source = "messageIds.first")
    @Mapping(target = "messageCount", source = "messageIds")
    MessageIdsTelegramCommandCallbackDataDTO map(MessageIdsCommandCallbackDataDTO callbackData);

    /**
     * Maps a {@code MessageIdsTelegramCommandCallbackDataDTO} to a {@code MessageIdsCommandCallbackDataDTO}.
     *
     * @param callbackData the source Telegram command callback data
     * @return the mapped command callback data
     */
    @Mapping(target = "messageIds", source = ".")
    MessageIdsCommandCallbackDataDTO map(MessageIdsTelegramCommandCallbackDataDTO callbackData);

    /**
     * Gets the size of a collection.
     *
     * @param collection the collection to get the size of
     * @return the size of the collection
     */
    default int getSize(Collection<?> collection) {
        return CollectionUtils.size(collection);
    }

    /**
     * Maps the message IDs from a {@code MessageIdsTelegramCommandCallbackDataDTO} to a list of integers.
     * This method generates a list of integers starting from the first message ID and continuing for the
     * specified number of messages.
     *
     * @param callbackData the source Telegram command callback data containing the first message ID and count
     * @return a list of message IDs
     */
    default List<Integer> mapMessageIds(MessageIdsTelegramCommandCallbackDataDTO callbackData) {
        return IntStream.range(
                        callbackData.getFirstMessageId(),
                        callbackData.getFirstMessageId() + callbackData.getMessageCount()
                )
                .boxed()
                .toList();
    }
}

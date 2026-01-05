package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData;
import io.github.yvasyliev.telegramforwarder.bot.dto.RawCommandCallbackData;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Mapper interface for converting Telegram command callback data to application-specific command callback data DTOs.
 */
@Mapper
public interface CommandCallbackDataMapper {
    /**
     * Converts {@link CommandCallbackData} to {@link RawCommandCallbackData}.
     *
     * @param callbackData the command callback data to convert
     * @return the raw command callback data
     */
    @Mapping(target = "firstMessageId", source = "messageIds.first")
    @Mapping(target = "messageCount", source = "messageIds")
    RawCommandCallbackData map(CommandCallbackData callbackData);

    /**
     * Maps {@link RawCommandCallbackData} to {@link CommandCallbackData}.
     *
     * @param callbackData the raw command callback data to map
     * @return the command callback data
     */
    @Mapping(target = "messageIds", source = ".")
    CommandCallbackData map(RawCommandCallbackData callbackData);

    /**
     * Maps inline keyboard button properties and associated messages to {@link CommandCallbackData}.
     *
     * @param ikbp     the inline keyboard button properties
     * @param messages the list of messages associated with the button
     * @return the command callback data
     */
    default CommandCallbackData map(
            PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties ikbp,
            @Context List<Message> messages
    ) {
        return doMap(ikbp, messages);
    }

    /**
     * Maps a {@link Message} to its message ID.
     *
     * @param message the message to map
     * @return the message ID
     */
    default Integer map(Message message) {
        return message.getMessageId();
    }

    /**
     * Internal mapping method to create {@link CommandCallbackData} from button properties and messages.
     *
     * @param ikbp     the inline keyboard button properties
     * @param messages the list of messages associated with the button
     * @return the command callback data
     */
    @Mapping(target = "messageIds", source = "messages")
    CommandCallbackData doMap(
            PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.InlineKeyboardButtonProperties ikbp,
            List<Message> messages
    );

    /**
     * Gets the size of a collection safely.
     *
     * @param collection the collection to get the size of
     * @return the size of the collection, or {@code 0} if the collection is {@code null}
     */
    default int getSize(Collection<?> collection) {
        return CollectionUtils.size(collection);
    }

    /**
     * Generates a list of message IDs based on the first message ID and message count.
     *
     * @param callbackData the raw command callback data containing the first message ID and message count
     * @return the list of message IDs
     */
    default List<Integer> getMessageIds(RawCommandCallbackData callbackData) {
        return IntStream.range(
                        callbackData.firstMessageId(),
                        callbackData.firstMessageId() + callbackData.messageCount()
                )
                .boxed()
                .toList();
    }
}

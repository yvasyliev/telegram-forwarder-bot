package io.github.yvasyliev.telegramforwarderbot.mapper;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Mapper
public interface MessageIdsCallbackDataMapper {
    @Mapping(target = "firstMessageId", source = "messageIds.first")
    @Mapping(target = "messageCount", source = "messageIds")
    MessageIdsTelegramCommandCallbackDataDTO map(MessageIdsCommandCallbackDataDTO callbackData);

    @Mapping(target = "messageIds", source = ".")
    MessageIdsCommandCallbackDataDTO map(MessageIdsTelegramCommandCallbackDataDTO callbackData);

    default int getSize(Collection<?> collection) {
        return CollectionUtils.size(collection);
    }

    default List<Integer> mapMessageIds(MessageIdsTelegramCommandCallbackDataDTO callbackData) {
        return IntStream.range(
                        callbackData.getFirstMessageId(),
                        callbackData.getFirstMessageId() + callbackData.getMessageCount()
                )
                .boxed()
                .toList();
    }
}

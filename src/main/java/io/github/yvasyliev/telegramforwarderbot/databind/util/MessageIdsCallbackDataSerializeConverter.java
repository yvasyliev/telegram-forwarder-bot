package io.github.yvasyliev.telegramforwarderbot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.mapper.MessageIdsCallbackDataMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageIdsCallbackDataSerializeConverter
        extends StdConverter<MessageIdsCommandCallbackDataDTO, MessageIdsTelegramCommandCallbackDataDTO> {
    private final MessageIdsCallbackDataMapper callbackDataMapper;

    @Override
    public MessageIdsTelegramCommandCallbackDataDTO convert(MessageIdsCommandCallbackDataDTO callbackData) {
        return callbackDataMapper.map(callbackData);
    }
}

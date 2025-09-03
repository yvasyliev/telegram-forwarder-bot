package io.github.yvasyliev.telegramforwarder.bot.databind.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.mapper.MessageIdsCallbackDataMapper;
import lombok.RequiredArgsConstructor;

/**
 * Converter for serializing {@link MessageIdsCommandCallbackDataDTO} to
 * {@link MessageIdsTelegramCommandCallbackDataDTO}.
 * This converter uses a mapper to perform the conversion.
 */
@RequiredArgsConstructor
public class MessageIdsCallbackDataSerializeConverter
        extends StdConverter<MessageIdsCommandCallbackDataDTO, MessageIdsTelegramCommandCallbackDataDTO> {
    private final MessageIdsCallbackDataMapper callbackDataMapper;

    @Override
    public MessageIdsTelegramCommandCallbackDataDTO convert(MessageIdsCommandCallbackDataDTO callbackData) {
        return callbackDataMapper.map(callbackData);
    }
}

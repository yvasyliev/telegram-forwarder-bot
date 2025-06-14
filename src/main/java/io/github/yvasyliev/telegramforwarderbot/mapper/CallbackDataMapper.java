package io.github.yvasyliev.telegramforwarderbot.mapper;

import io.github.yvasyliev.telegramforwarderbot.dto.AbstractCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.AbstractTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(uses = MessageIdsCallbackDataMapper.class)
public interface CallbackDataMapper {
    @SubclassMapping(
            target = MessageIdsCommandCallbackDataDTO.class,
            source = MessageIdsTelegramCommandCallbackDataDTO.class
    )
    AbstractCommandCallbackDataDTO map(AbstractTelegramCommandCallbackDataDTO callbackData);
}

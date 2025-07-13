package io.github.yvasyliev.telegramforwarderbot.mapper;

import io.github.yvasyliev.telegramforwarderbot.dto.AbstractCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.AbstractTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

/**
 * Mapper interface for converting Telegram command callback data to application-specific command callback data DTOs.
 * It uses MapStruct to generate the implementation at compile time.
 */
@Mapper(uses = MessageIdsCallbackDataMapper.class)
public interface CallbackDataMapper {
    /**
     * Maps an abstract Telegram command callback data DTO to an application-specific command callback data DTO.
     *
     * @param callbackData the abstract Telegram command callback data DTO
     * @return the mapped application-specific command callback data DTO
     */
    @SubclassMapping(
            target = MessageIdsCommandCallbackDataDTO.class,
            source = MessageIdsTelegramCommandCallbackDataDTO.class
    )
    AbstractCommandCallbackDataDTO map(AbstractTelegramCommandCallbackDataDTO callbackData);
}

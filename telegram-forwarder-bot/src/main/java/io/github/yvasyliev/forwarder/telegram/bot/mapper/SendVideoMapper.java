package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;

/**
 * {@link SendVideo} mapper.
 */
@Mapper(uses = InputFileMapper.class)
public interface SendVideoMapper {
    /**
     * Maps {@link SendVideoDTO} and {@link TelegramAdminProperties} to {@link SendVideo}.
     *
     * @param sendVideoDTO   the send video DTO
     * @param adminProperties the Telegram admin properties
     * @return the mapped {@link SendVideo}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    @Mapping(target = "supportsStreaming", constant = BooleanUtils.TRUE)
    SendVideo map(SendVideoDTO sendVideoDTO, TelegramAdminProperties adminProperties);
}

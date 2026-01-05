package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.SendPhotoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * {@link SendPhoto} mapper.
 */
@Mapper(uses = InputFileMapper.class)
public interface SendPhotoMapper {
    /**
     * Maps {@link SendPhotoDTO} and {@link TelegramAdminProperties} to {@link SendPhoto}.
     *
     * @param sendPhotoDTO   the send photo DTO
     * @param adminProperties the Telegram admin properties
     * @return the mapped {@link SendPhoto}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    SendPhoto map(SendPhotoDTO sendPhotoDTO, TelegramAdminProperties adminProperties);
}

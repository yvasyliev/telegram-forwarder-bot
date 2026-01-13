package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendAnimationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;

/**
 * {@link SendAnimation} mapper.
 */
@Mapper(uses = InputFileMapper.class)
public interface SendAnimationMapper {
    /**
     * Maps {@link SendAnimationDTO} and {@link TelegramAdminProperties} to {@link SendAnimation}.
     *
     * @param sendAnimation   the {@link SendAnimationDTO}
     * @param adminProperties the {@link TelegramAdminProperties}
     * @return the {@link SendAnimation}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    SendAnimation map(SendAnimationDTO sendAnimation, TelegramAdminProperties adminProperties);
}

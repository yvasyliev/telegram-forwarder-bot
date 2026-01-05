package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.TelegramBotProperties;
import io.github.yvasyliev.telegramforwarder.bot.dto.BotDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Bot DTO mapper.
 */
@Mapper
public interface BotDTOMapper {
    /**
     * Maps {@link User} and {@link TelegramBotProperties} to {@link BotDTO}.
     *
     * @param bot           the Telegram bot user
     * @param botProperties the Telegram bot properties
     * @return the mapped {@link BotDTO}
     */
    @Mapping(target = "name", source = "bot.firstName")
    BotDTO map(User bot, TelegramBotProperties botProperties);
}

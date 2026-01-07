package io.github.yvasyliev.forwarder.telegram.logging.mapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.thymeleaf.TelegramTemplateProcessor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * {@link SendMessage} mapper.
 */
@Mapper(uses = {LoggingTemplateContextMapper.class, TelegramTemplateProcessor.class})
public interface LoggingSendMessageMapper {
    /**
     * Maps logging event to {@link SendMessage}.
     *
     * @param adminProperties the admin properties
     * @param event           the logging event
     * @return the mapped {@link SendMessage}
     */
    @Mapping(target = "chatId", source = "adminProperties.id")
    @Mapping(target = "text", source = "event")
    @Mapping(target = "parseMode", constant = ParseMode.HTML)
    SendMessage map(TelegramAdminProperties adminProperties, ILoggingEvent event);
}

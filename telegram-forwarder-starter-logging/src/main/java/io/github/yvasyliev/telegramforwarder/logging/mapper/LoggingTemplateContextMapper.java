package io.github.yvasyliev.telegramforwarder.logging.mapper;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * {@link TemplateContext} mapper.
 */
@Mapper(uses = {LoggingContextMapper.class, PatternLayout.class})
public interface LoggingTemplateContextMapper {
    /**
     * Maps an {@link ILoggingEvent} to a {@link TemplateContext}.
     *
     * @param event the logging event
     * @return the mapped template context
     */
    @Mapping(target = "template", constant = "error")
    @Mapping(target = "context", source = ".")
    TemplateContext map(ILoggingEvent event);
}

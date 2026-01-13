package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import io.github.yvasyliev.forwarder.telegram.thymeleaf.TemplateContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * {@link TemplateContext} mapper.
 */
@Mapper(uses = ContextMapper.class)
public interface TemplateContextMapper {
    /**
     * Maps {@link SendUrlDTO} to {@link TemplateContext}.
     *
     * @param sendUrl the {@link SendUrlDTO}
     * @return the mapped {@link TemplateContext}
     */
    @Mapping(target = "template", constant = "url")
    @Mapping(target = "context", source = ".")
    TemplateContext map(SendUrlDTO sendUrl);

    /**
     * Maps {@link String} to {@link TemplateContext}.
     *
     * @param template the {@link String}
     * @return the mapped {@link TemplateContext}
     */
    @Mapping(target = "template", source = ".")
    @Mapping(target = "context", source = ".")
    TemplateContext map(String template);
}

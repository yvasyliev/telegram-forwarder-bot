package io.github.yvasyliev.telegramforwarder.logging.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

/**
 * {@link IContext} mapper.
 */
@Mapper
public interface LoggingContextMapper {
    /**
     * Maps a {@link String} to an {@link IContext}.
     *
     * @param text the text
     * @return the {@link IContext}
     */
    @BeanMapping(resultType = Context.class)
    IContext map(String text);

    /**
     * Sets the text variable in the context after mapping.
     *
     * @param context the mapping target context
     * @param text    the text to set
     */
    @AfterMapping
    default void setText(@MappingTarget AbstractContext context, String text) {
        context.setVariable("text", text);
    }
}

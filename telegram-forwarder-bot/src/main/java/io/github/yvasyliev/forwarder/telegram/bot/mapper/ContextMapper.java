package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
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
public interface ContextMapper {
    /**
     * Maps {@link SendUrlDTO} to {@link IContext}.
     *
     * @param sendUrl the {@link SendUrlDTO}
     * @return the {@link IContext}
     */
    @BeanMapping(resultType = Context.class)
    IContext map(SendUrlDTO sendUrl);

    /**
     * Maps {@link String} to {@link IContext}.
     *
     * @param str the {@link String}
     * @return the {@link IContext}
     */
    @BeanMapping(resultType = Context.class)
    IContext map(String str);

    /**
     * Sets variables in the {@link AbstractContext} after mapping.
     *
     * @param context the {@link AbstractContext}
     * @param sendUrl the {@link SendUrlDTO}
     */
    @AfterMapping
    default void setVariables(@MappingTarget AbstractContext context, SendUrlDTO sendUrl) {
        context.setVariable("text", sendUrl.text());
        context.setVariable("url", sendUrl.url());
    }
}

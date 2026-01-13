package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.PostControlsAnswerCallbackQueryProperties;
import io.github.yvasyliev.forwarder.telegram.bot.configuration.UnauthorizedActionProperties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * {@link AnswerCallbackQuery} mapper.
 */
@Mapper
public interface AnswerCallbackQueryMapper {
    /**
     * Maps a {@link CallbackQuery} and {@link PostControlsAnswerCallbackQueryProperties.AnswerCallbackQueryProperties}.
     *
     * @param callbackQuery                 the callback query
     * @param answerCallbackQueryProperties the answer callback query properties
     * @return the mapped AnswerCallbackQuery
     */
    @Mapping(target = "callbackQueryId", source = "callbackQuery.id")
    AnswerCallbackQuery map(
            CallbackQuery callbackQuery,
            PostControlsAnswerCallbackQueryProperties.AnswerCallbackQueryProperties answerCallbackQueryProperties
    );

    /**
     * Maps a {@link CallbackQuery} and {@link UnauthorizedActionProperties}.
     *
     * @param callbackQuery                the callback query
     * @param unauthorizedActionProperties the unauthorized action properties
     * @return the mapped AnswerCallbackQuery
     */
    @Mapping(target = "callbackQueryId", source = "callbackQuery.id")
    AnswerCallbackQuery map(CallbackQuery callbackQuery, UnauthorizedActionProperties unauthorizedActionProperties);
}

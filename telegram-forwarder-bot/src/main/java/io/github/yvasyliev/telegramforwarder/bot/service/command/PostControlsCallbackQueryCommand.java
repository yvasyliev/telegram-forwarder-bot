package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;

/**
 * A command interface for handling callback queries related to post controls.
 * This interface extends the generic CallbackQueryCommand with a specific type
 * of callback data, {@link MessageIdsCommandCallbackDataDTO}.
 */
@FunctionalInterface
public interface PostControlsCallbackQueryCommand extends CallbackQueryCommand<MessageIdsCommandCallbackDataDTO> {}

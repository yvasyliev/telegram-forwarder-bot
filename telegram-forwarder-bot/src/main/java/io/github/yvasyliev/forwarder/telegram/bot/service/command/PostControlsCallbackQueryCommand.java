package io.github.yvasyliev.forwarder.telegram.bot.service.command;

import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;

/**
 * A command interface for handling callback queries related to post controls.
 * This interface extends the generic CallbackQueryCommand with a specific type
 * of callback data, {@link CommandCallbackData}.
 */
@FunctionalInterface
public interface PostControlsCallbackQueryCommand extends CallbackQueryCommand<CommandCallbackData> {}

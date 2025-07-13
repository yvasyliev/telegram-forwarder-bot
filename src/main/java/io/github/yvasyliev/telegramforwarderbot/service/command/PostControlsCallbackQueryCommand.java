package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;

/**
 * A command interface for handling callback queries related to post controls.
 * This interface extends the generic CallbackQueryCommand with a specific type
 * of callback data, {@link MessageIdsCommandCallbackDataDTO}.
 */
@FunctionalInterface
public interface PostControlsCallbackQueryCommand extends CallbackQueryCommand<MessageIdsCommandCallbackDataDTO> {}

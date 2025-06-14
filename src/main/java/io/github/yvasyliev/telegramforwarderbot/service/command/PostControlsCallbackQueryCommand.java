package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;

@FunctionalInterface
public interface PostControlsCallbackQueryCommand extends CallbackQueryCommand<MessageIdsCommandCallbackDataDTO> {}

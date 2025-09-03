package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.service.ApprovedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * Rejects a post by deleting it from the approved posts.
 */
@Service("/rejectpost")
@RequiredArgsConstructor
public class RejectPostCallbackQueryCommand implements PostControlsCallbackQueryCommand {
    private final ApprovedPostService postService;

    @Override
    public void execute(CallbackQuery callbackQuery, MessageIdsCommandCallbackDataDTO callbackData) {
        postService.delete(callbackData.getMessageIds());
    }
}

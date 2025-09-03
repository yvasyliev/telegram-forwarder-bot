package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.service.ApprovedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * Rejects a text post by removing caption from the approved posts.
 */
@Service("/rejecttext")
@RequiredArgsConstructor
public class RejectTextCallbackQueryCommand implements PostControlsCallbackQueryCommand {
    private final ApprovedPostService postService;

    @Override
    public void execute(CallbackQuery callbackQuery, MessageIdsCommandCallbackDataDTO callbackData) {
        postService.save(callbackData.getMessageIds(), true);
    }
}

package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.service.ApprovedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

/**
 * Approves a post by saving its message IDs.
 */
@Service("/approvepost")
@RequiredArgsConstructor
public class ApprovePostCallbackQueryCommand implements PostControlsCallbackQueryCommand {
    private final ApprovedPostService postService;

    @Override
    public void execute(CallbackQuery callbackQuery, MessageIdsCommandCallbackDataDTO callbackData) {
        postService.save(callbackData.getMessageIds(), false);
    }
}

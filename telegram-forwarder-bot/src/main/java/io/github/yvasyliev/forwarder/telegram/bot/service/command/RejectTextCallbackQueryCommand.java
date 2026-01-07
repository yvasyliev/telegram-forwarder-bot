package io.github.yvasyliev.forwarder.telegram.bot.service.command;

import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import io.github.yvasyliev.forwarder.telegram.bot.service.ApprovedPostService;
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
    public void execute(CallbackQuery callbackQuery, CommandCallbackData callbackData) {
        postService.save(callbackData.messageIds(), true);
    }
}

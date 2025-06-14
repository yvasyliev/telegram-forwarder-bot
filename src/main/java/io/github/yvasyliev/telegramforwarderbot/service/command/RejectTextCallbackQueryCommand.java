package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.service.ApprovedPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service("/rejecttext")
@RequiredArgsConstructor
public class RejectTextCallbackQueryCommand implements PostControlsCallbackQueryCommand {
    private final ApprovedPostService postService;

    @Override
    public void execute(CallbackQuery callbackQuery, MessageIdsCommandCallbackDataDTO callbackData) {
        postService.save(callbackData.getMessageIds(), true);
    }
}

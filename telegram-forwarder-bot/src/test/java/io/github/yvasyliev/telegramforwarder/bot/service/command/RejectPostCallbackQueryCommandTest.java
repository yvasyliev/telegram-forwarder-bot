package io.github.yvasyliev.telegramforwarder.bot.service.command;

import io.github.yvasyliev.telegramforwarder.bot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.service.ApprovedPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RejectPostCallbackQueryCommandTest {
    @InjectMocks private RejectPostCallbackQueryCommand command;
    @Mock private ApprovedPostService postService;

    @Test
    void testExecute() {
        var callbackData = new MessageIdsCommandCallbackDataDTO();
        var messageIds = List.of(123, 456);

        callbackData.setMessageIds(messageIds);

        command.execute(mock(), callbackData);

        verify(postService).delete(messageIds);
    }
}

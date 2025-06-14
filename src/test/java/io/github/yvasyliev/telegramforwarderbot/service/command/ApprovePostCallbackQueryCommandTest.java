package io.github.yvasyliev.telegramforwarderbot.service.command;

import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.service.ApprovedPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApprovePostCallbackQueryCommandTest {
    @InjectMocks private ApprovePostCallbackQueryCommand command;
    @Mock private ApprovedPostService postService;

    @Test
    void testExecute() {
        var callbackData = new MessageIdsCommandCallbackDataDTO();
        var messageIds = List.of(123, 456);

        callbackData.setMessageIds(messageIds);

        command.execute(mock(), callbackData);

        verify(postService).save(messageIds, false);
    }
}
package io.github.yvasyliev.forwarder.telegram.bot.service.command;

import io.github.yvasyliev.forwarder.telegram.bot.dto.CommandCallbackData;
import io.github.yvasyliev.forwarder.telegram.bot.service.ApprovedPostService;
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
        var messageIds = List.of(123, 456);
        var callbackData = new CommandCallbackData(null, messageIds);

        command.execute(mock(), callbackData);

        verify(postService).save(messageIds, false);
    }
}

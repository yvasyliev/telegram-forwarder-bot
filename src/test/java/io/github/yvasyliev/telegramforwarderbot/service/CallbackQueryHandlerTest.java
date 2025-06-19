package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.dto.AbstractCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.AbstractTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.dto.MessageIdsTelegramCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarderbot.mapper.CallbackDataMapper;
import io.github.yvasyliev.telegramforwarderbot.service.command.CallbackQueryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallbackQueryHandlerTest {
    private static final String DATA = "testData";
    private static final String COMMAND_NAME = "testCommand";
    @InjectMocks private CallbackQueryHandler callbackQueryHandler;
    @Mock private CallbackDataConverter callbackDataConverter;
    @Mock private CallbackDataMapper callbackDataMapper;
    @Mock private ApplicationContext applicationContext;
    @Mock private CallbackQueryCommand<AbstractCommandCallbackDataDTO> callbackQueryCommand;
    private final AbstractCommandCallbackDataDTO callbackData = new MessageIdsCommandCallbackDataDTO();
    private final CallbackQuery callbackQuery = new CallbackQuery();

    @BeforeEach
    void setUp() {
        var telegramCallbackData = mock(MessageIdsTelegramCommandCallbackDataDTO.class);

        callbackQuery.setData(DATA);
        callbackData.setCommand(COMMAND_NAME);

        when(callbackDataConverter.fromCallbackData(DATA, AbstractTelegramCommandCallbackDataDTO.class))
                .thenReturn(telegramCallbackData);
        when(callbackDataMapper.map(telegramCallbackData)).thenReturn(callbackData);
    }

    @Test
    void shouldExecuteCommand() {
        when(applicationContext.getBean(COMMAND_NAME, CallbackQueryCommand.class)).thenReturn(callbackQueryCommand);

        callbackQueryHandler.handle(callbackQuery);

        verify(callbackQueryCommand).execute(callbackQuery, callbackData);
    }

    @Test
    void shouldHandleUnknownCommand() {
        when(applicationContext.getBean(COMMAND_NAME, CallbackQueryCommand.class))
                .thenThrow(NoSuchBeanDefinitionException.class);

        assertDoesNotThrow(() -> callbackQueryHandler.handle(callbackQuery));
    }
}

package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.bot.configuration.PostControlsSendMessageProperties;
import io.github.yvasyliev.telegramforwarder.bot.configuration.UnauthorizedActionProperties;
import io.github.yvasyliev.telegramforwarder.bot.util.TelegramBotTemplateProcessor;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.SendUrlDTO;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContext;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendMessageMapperTest {
    @InjectMocks private SendMessageMapperImpl sendMessageMapper;
    @Mock private TelegramBotTemplateProcessor telegramBotTemplateProcessor;
    @Mock private ReplyKeyboardMapper replyKeyboardMapper;
    @Mock private TemplateContextMapper templateContextMapper;

    @Nested
    class SendUrlDTOMappingTest {
        @Test
        void testMapNull() {
            var actual = sendMessageMapper.map((SendUrlDTO) null, null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var templateContext = mock(TemplateContext.class);
            var text = "text";
            var id = 12345L;
            var sendUrlDTO = mock(SendUrlDTO.class);
            var adminProperties = new TelegramAdminProperties(id);
            var expected = SendMessage.builder().chatId(id).text(text).build();

            when(templateContextMapper.map(sendUrlDTO)).thenReturn(templateContext);
            when(telegramBotTemplateProcessor.process(templateContext)).thenReturn(text);

            var actual = sendMessageMapper.map(sendUrlDTO, adminProperties);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class PostControlsSendMessagePropertiesMappingTest {
        @Test
        void testMapNull() {
            var actual = sendMessageMapper.map((PostControlsSendMessageProperties) null, null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var text = "text";
            var chatId = 12345L;
            var replyMarkup = mock(PostControlsSendMessageProperties.InlineKeyboardMarkupProperties.class);
            var replyKeyboard = mock(ReplyKeyboard.class);
            var postControlsSendMessageProperties = new PostControlsSendMessageProperties(text, replyMarkup);
            var messages = List.of((Message) Message.builder().chat(new Chat(chatId, "chat")).build());
            var expected = SendMessage.builder().chatId(chatId).text(text).replyMarkup(replyKeyboard).build();

            when(replyKeyboardMapper.map(replyMarkup, messages)).thenReturn(replyKeyboard);

            var actual = sendMessageMapper.map(postControlsSendMessageProperties, messages);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class MessageTemplateMappingTest {
        @Test
        void testMapNull() {
            var actual = sendMessageMapper.map((Message) null, (String) null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var chatId = 12345L;
            var template = "template";
            var text = "text";
            var templateContext = mock(TemplateContext.class);
            var message = Message.builder().chat(new Chat(chatId, "chat")).build();
            var expected = SendMessage.builder().chatId(chatId).text(text).parseMode(ParseMode.HTML).build();

            when(templateContextMapper.map(template)).thenReturn(templateContext);
            when(telegramBotTemplateProcessor.process(templateContext)).thenReturn(text);

            var actual = sendMessageMapper.map(message, template);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class UnauthorizedActionMappingTest {
        @Test
        void testMapNull() {
            var actual = sendMessageMapper.map(null, (UnauthorizedActionProperties) null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var chatId = 12345L;
            var text = "text";
            var message = Message.builder().chat(new Chat(chatId, "chat")).build();
            var unauthorizedActionProperties = new UnauthorizedActionProperties(text);
            var expected = SendMessage.builder().chatId(chatId).text(text).build();

            var actual = sendMessageMapper.map(message, unauthorizedActionProperties);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class AdminPropertiesTemplateMappingTest {
        @Test
        void testMapNull() {
            var actual = sendMessageMapper.map((TelegramAdminProperties) null, null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var id = 12345L;
            var template = "template";
            var text = "text";
            var templateContext = mock(TemplateContext.class);
            var adminProperties = new TelegramAdminProperties(id);
            var expected = SendMessage.builder().chatId(id).text(text).parseMode(ParseMode.HTML).build();

            when(templateContextMapper.map(template)).thenReturn(templateContext);
            when(telegramBotTemplateProcessor.process(templateContext)).thenReturn(text);

            var actual = sendMessageMapper.map(adminProperties, template);

            assertEquals(expected, actual);
        }
    }
}

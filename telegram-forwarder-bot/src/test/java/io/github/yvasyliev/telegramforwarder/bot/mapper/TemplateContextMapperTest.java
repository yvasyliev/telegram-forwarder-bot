package io.github.yvasyliev.telegramforwarder.bot.mapper;

import io.github.yvasyliev.telegramforwarder.core.dto.SendUrlDTO;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContext;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.IContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateContextMapperTest {
    @InjectMocks private TemplateContextMapperImpl templateContextMapper;
    @Mock private ContextMapper contextMapper;

    @Nested
    class SendUrlDTOMappingTest {
        @Test
        void testMapNull() {
            var actual = templateContextMapper.map((SendUrlDTO) null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var sendUrl = mock(SendUrlDTO.class);
            var context = mock(IContext.class);
            var expected = new TemplateContext("url", context);

            when(contextMapper.map(sendUrl)).thenReturn(context);

            var actual = templateContextMapper.map(sendUrl);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class TemplateMappingTest {
        @Test
        void testMapNull() {
            var actual = templateContextMapper.map((String) null);

            assertNull(actual);
        }

        @Test
        void testMap() {
            var template = "testTemplate";
            var context = mock(IContext.class);
            var expected = new TemplateContext(template, context);

            when(contextMapper.map(template)).thenReturn(context);

            var actual = templateContextMapper.map(template);

            assertEquals(expected, actual);
        }
    }
}

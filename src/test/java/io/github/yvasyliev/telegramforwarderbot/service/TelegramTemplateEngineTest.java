package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramBotAppenderProperties;
import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.EvaluationContext;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramTemplateEngineTest {
    private static final String TEMPLATE_NAME = "templateName";
    private static final String EXPECTED = "expected";
    @InjectMocks TelegramTemplateEngine telegramTemplateEngine;
    @Mock private TelegramProperties telegramProperties;
    @Mock private TelegramBotAppenderProperties appenderProperties;
    @Mock private EvaluationContext evaluationContext;
    @Mock private ITemplateEngine templateEngine;
    private final AbstractContext context = new Context();

    @BeforeEach
    void setUp() {
        context.setVariable("telegramProperties", telegramProperties);
        context.setVariable("appenderProperties", appenderProperties);
        context.setVariable(
                ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                evaluationContext
        );

        when(templateEngine.process(eq(TEMPLATE_NAME), refEq(context))).thenReturn(EXPECTED);
    }

    @AfterEach
    void tearDown() {
        verify(templateEngine).process(eq(TEMPLATE_NAME), refEq(context));
    }

    @Test
    void shouldProcessWithDefaultContext() {
        var actual = telegramTemplateEngine.process(TEMPLATE_NAME);

        assertEquals(EXPECTED, actual);
    }

    @Test
    void shouldProcessWithCustomContext() {
        context.setVariable("customVariable", "customValue");

        var actual = telegramTemplateEngine.process(TEMPLATE_NAME, context);

        assertEquals(EXPECTED, actual);
    }
}
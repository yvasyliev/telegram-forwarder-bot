package io.github.yvasyliev.telegramforwarder.thymeleaf;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring6.ISpringTemplateEngine;

@RequiredArgsConstructor
public class TelegramTemplateEngine implements ITemplateEngine {
    @Delegate private final ISpringTemplateEngine delegate;
}

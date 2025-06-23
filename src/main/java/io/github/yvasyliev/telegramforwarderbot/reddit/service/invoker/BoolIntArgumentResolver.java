package io.github.yvasyliev.telegramforwarderbot.reddit.service.invoker;

import io.github.yvasyliev.telegramforwarderbot.reddit.annotation.BoolInt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.invoker.HttpRequestValues;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;

/**
 * Resolves boolean parameters annotated with {@link BoolInt} to integers ({@code 0} or {@code 1})
 * for use in HTTP requests, specifically for Reddit API parameters that expect
 * boolean values represented as integers.
 */
@Component
public class BoolIntArgumentResolver implements HttpServiceArgumentResolver {
    @Override
    public boolean resolve(
            Object argument,
            MethodParameter parameter,
            @NonNull HttpRequestValues.Builder requestValues
    ) {
        var requestParam = parameter.getParameterAnnotation(RequestParam.class);

        if (argument instanceof Boolean boolInt
                && requestParam != null
                && parameter.hasParameterAnnotation(BoolInt.class)) {
            var name = StringUtils.getIfBlank(requestParam.name(), parameter::getParameterName);
            var value = boolInt ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;

            requestValues.addRequestParameter(name, value.toString());

            return true;
        }

        return false;
    }
}

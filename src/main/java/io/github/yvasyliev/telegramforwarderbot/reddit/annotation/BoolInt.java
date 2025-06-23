package io.github.yvasyliev.telegramforwarderbot.reddit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a parameter should be treated as a boolean value
 * but represented as an integer ({@code 0} or {@code 1}) in the context of Reddit API.
 * This is used for parameters that expect a boolean value but are represented
 * as integers in the API.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface BoolInt {}

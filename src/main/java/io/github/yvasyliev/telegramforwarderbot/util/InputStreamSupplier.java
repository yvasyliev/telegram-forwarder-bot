package io.github.yvasyliev.telegramforwarderbot.util;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface InputStreamSupplier {
    InputStream get() throws IOException;
}

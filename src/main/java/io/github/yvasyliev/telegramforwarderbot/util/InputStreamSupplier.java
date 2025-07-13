package io.github.yvasyliev.telegramforwarderbot.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Functional interface for providing an InputStream.
 * Implementations should define how to obtain the InputStream.
 */
@FunctionalInterface
public interface InputStreamSupplier {
    /**
     * Gets an InputStream.
     *
     * @return the InputStream
     * @throws IOException if an I/O error occurs while obtaining the InputStream
     */
    InputStream get() throws IOException;
}

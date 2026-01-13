package io.github.yvasyliev.forwarder.telegram.core.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * A supplier that provides instances of {@link Closeable} objects.
 *
 * @param <T> the type of {@link Closeable} object supplied
 */
@FunctionalInterface
public interface CloseableSupplier<T extends Closeable> {
    /**
     * Gets an instance of a {@link Closeable} object.
     *
     * @return an instance of a {@link Closeable} object
     * @throws IOException if an I/O error occurs
     */
    T get() throws IOException;
}

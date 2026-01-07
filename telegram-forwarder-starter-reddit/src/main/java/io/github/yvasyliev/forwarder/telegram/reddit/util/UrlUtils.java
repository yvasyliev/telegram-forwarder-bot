package io.github.yvasyliev.forwarder.telegram.reddit.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utility class for {@link URL}-related operations.
 */
@UtilityClass
public class UrlUtils {
    /**
     * Opens an {@link InputStream} from the given {@link URL}.
     *
     * @param url the URL to open the stream from
     * @return the opened {@link InputStream}
     * @throws IOException if an I/O error occurs
     */
    public InputStream openStream(URL url) throws IOException {
        return url.openStream();
    }

    /**
     * Extracts the file name from the given {@link URL}.
     *
     * @param url the {@link URL} to extract the file name from
     * @return the extracted file name
     */
    public String getFileName(URL url) {
        return FilenameUtils.getName(url.getPath());
    }
}

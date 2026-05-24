package io.github.yvasyliev.forwarder.telegram.core.util;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputFileDTO;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Converter class to convert a {@link URL} to an {@link InputFileDTO}.
 */
public class InputFileDTOConverter {
    /**
     * Converts a {@link URL} to an {@link InputFileDTO}.
     *
     * @param url the URL to convert
     * @return the converted {@link InputFileDTO}
     * @throws IOException if an I/O error occurs while opening the stream from the URL
     */
    public InputFileDTO convert(URL url) throws IOException {
        return new InputFileDTO(
                url.openStream(),
                FilenameUtils.getName(URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8))
        );
    }
}

package io.github.yvasyliev.forwarder.telegram.core.util;

import io.github.yvasyliev.forwarder.telegram.core.dto.InputMediaPhotoDTO;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Converter class to convert a {@link URL} to an {@link InputMediaPhotoDTO}.
 */
public class InputMediaPhotoDTOConverter {
    /**
     * Converts a {@link URL} to an {@link InputMediaPhotoDTO}.
     *
     * @param url the URL to convert
     * @return the converted {@link InputMediaPhotoDTO}
     * @throws IOException if an I/O error occurs while opening the stream from the URL
     */
    public InputMediaPhotoDTO convert(URL url) throws IOException {
        return new InputMediaPhotoDTO(
                url.openStream(),
                FilenameUtils.getName(URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8)),
                null
        );
    }
}

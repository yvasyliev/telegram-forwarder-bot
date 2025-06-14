package io.github.yvasyliev.telegramforwarderbot.dto;

import io.github.yvasyliev.telegramforwarderbot.util.InputStreamSupplier;
import org.apache.commons.io.FilenameUtils;

import java.net.URL;

public record InputFileDTO(InputStreamSupplier fileSupplier, String filename, boolean hasSpoiler) {
    public static InputFileDTO fromUrl(URL url, boolean hasSpoiler) {
        return new InputFileDTO(url::openStream, FilenameUtils.getName(url.getPath()), hasSpoiler);
    }
}

package io.github.yvasyliev.telegramforwarder.core.dto;

import io.github.yvasyliev.telegramforwarder.core.util.InputStreamSupplier;
import org.apache.commons.io.FilenameUtils;

import java.net.URL;

/**
 * Data Transfer Object for input files.
 *
 * @param fileSupplier a supplier for the input stream of the file
 * @param filename     the name of the file
 * @param hasSpoiler   indicates if the file has a spoiler
 */
public record InputFileDTO(InputStreamSupplier fileSupplier, String filename, boolean hasSpoiler) {
    /**
     * Creates an InputFileDTO from a URL.
     *
     * @param url        the URL of the file
     * @param hasSpoiler indicates if the file has a spoiler
     * @return a new InputFileDTO instance
     */
    public static InputFileDTO fromUrl(URL url, boolean hasSpoiler) {
        return new InputFileDTO(url::openStream, FilenameUtils.getName(url.getPath()), hasSpoiler);
    }
}

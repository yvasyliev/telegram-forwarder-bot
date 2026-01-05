package io.github.yvasyliev.telegramforwarder.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;
import java.io.InputStream;

/**
 * Data Transfer Object representing an input file with a media stream and file name.
 *
 * @param mediaStream the input stream of the media file
 * @param fileName    the name of the file
 */
public record InputFileDTO(@Delegate(types = Closeable.class) InputStream mediaStream, String fileName)
        implements Closeable {}

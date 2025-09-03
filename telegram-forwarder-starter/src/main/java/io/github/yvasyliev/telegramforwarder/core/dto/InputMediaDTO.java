package io.github.yvasyliev.telegramforwarder.core.dto;

/**
 * Data Transfer Object for input media used in Telegram.
 * It can represent different types of media such as animation, photo, or video.
 *
 * @param type      the type of the media
 * @param inputFile the input file associated with the media
 */
public record InputMediaDTO(Type type, InputFileDTO inputFile) {
    /**
     * Creates an {@link InputMediaDTO} for an animation.
     *
     * @param animation the input file representing the animation
     * @return a new {@link InputMediaDTO} instance for the animation
     */
    public static InputMediaDTO animation(InputFileDTO animation) {
        return new InputMediaDTO(Type.ANIMATION, animation);
    }

    /**
     * Creates an {@link InputMediaDTO} for a photo.
     *
     * @param photo the input file representing the photo
     * @return a new {@link InputMediaDTO} instance for the photo
     */
    public static InputMediaDTO photo(InputFileDTO photo) {
        return new InputMediaDTO(Type.PHOTO, photo);
    }

    /**
     * Types of media that can be represented in an {@link InputMediaDTO}.
     */
    public enum Type {
        /**
         * Represents an animation media type.
         */
        ANIMATION,

        /**
         * Represents a photo media type.
         */
        PHOTO,

        /**
         * Represents a video media type.
         */
        VIDEO
    }
}

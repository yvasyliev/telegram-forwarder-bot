package io.github.yvasyliev.telegramforwarderbot.dto;

public record InputMediaDTO(Type type, InputFileDTO inputFile) {
    public static InputMediaDTO animation(InputFileDTO animation) {
        return new InputMediaDTO(Type.ANIMATION, animation);
    }

    public static InputMediaDTO photo(InputFileDTO photo) {
        return new InputMediaDTO(Type.PHOTO, photo);
    }

    public enum Type {
        ANIMATION,
        PHOTO,
        VIDEO
    }
}

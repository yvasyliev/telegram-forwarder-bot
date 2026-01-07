package io.github.yvasyliev.forwarder.telegram.bot.dto;

/**
 * Data Transfer Object representing a Bot.
 *
 * @param name    the name of the bot
 * @param version the version of the bot
 */
public record BotDTO(String name, String version) {}

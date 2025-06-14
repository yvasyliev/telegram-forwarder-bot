package io.github.yvasyliev.telegramforwarderbot.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Listing(
        @JsonProperty("after") String after,
        @JsonProperty("dist") Integer dist,
        @JsonProperty("modhash") String modhash,
        @JsonProperty("geo_filter") String geoFilter,
        @JsonProperty("children") List<Thing<Link>> children,
        @JsonProperty("before") String before
) {
}

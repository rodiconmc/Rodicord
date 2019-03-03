package com.rodiconmc.rodicord.objects.channel;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.rodiconmc.rodicord.Bot;
import com.rodiconmc.rodicord.DiscordException;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class Channel {
    private String id;
    private Type type;

    Channel() {
        //no-args constructor
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    /**
     * Modifies a specific field in a channel. May throw a {@link com.rodiconmc.rodicord.DiscordException} if the field
     * edited is not avaliable in the current channel type.
     * @param field The name of the field
     * @param value The String value to set
     * @param bot The bot to perform the action as
     * @return The new channel
     * @see <a href="https://discordapp.com/developers/docs/resources/channel#modify-channel-json-params">
     *     Discord Modify Channel Params</a>
     */
    public Channel modifyChannel(@NotNull String field, @NotNull String value, @NotNull Bot bot) {
        JsonObject data = new JsonObject();
        data.addProperty(field, value);
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/channels/%s", getId())))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(bot.gson.toJson(data)))
                .build();
        HttpResponse<String> response = bot.makeRequest(request);
        return convertToNative(response, bot);
    }

    /**
     * Modifies a specific field in a channel. May throw a {@link com.rodiconmc.rodicord.DiscordException} if the field
     * edited is not avaliable in the current channel type.
     * @param field The name of the field
     * @param value The int value to set
     * @param bot The bot to perform the action as
     * @return The new channel
     * @see <a href="https://discordapp.com/developers/docs/resources/channel#modify-channel-json-params">
     *     Discord Modify Channel Params</a>
     */
    public Channel modifyChannel(@NotNull String field, @NotNull int value, @NotNull Bot bot) {
        JsonObject data = new JsonObject();
        data.addProperty(field, value);
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/channels/%s", getId())))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(bot.gson.toJson(data)))
                .build();
        HttpResponse<String> response = bot.makeRequest(request);
        return convertToNative(response, bot);
    }

    public void deleteChannel(Bot bot) {
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/channels/%s", getId())))
                .DELETE()
                .build();
        bot.makeRequest(request);
    }

    private Channel convertToNative(HttpResponse<String> json, Bot bot) {
        Type channelType = bot.gson.fromJson(bot.gson.fromJson(json.body(), JsonObject.class)
                .getAsJsonPrimitive("type"), Type.class);
        switch (channelType) {
            case DM:
                return bot.gson.fromJson(json.body(), DmChannel.class);
            case GROUP_DM:
                return bot.gson.fromJson(json.body(), GroupDmChannel.class);
            case GUILD_TEXT:
                return bot.gson.fromJson(json.body(), GuildChannel.class);
            case GUILD_VOICE:
                return bot.gson.fromJson(json.body(), VoiceChannel.class);
            case GUILD_CATEGORY:
                return bot.gson.fromJson(json.body(), CategoryChannel.class);
            default:
                throw new DiscordException(80003, "Unknown channel type");
        }
    }

    public enum Type {
        @SerializedName("0")
        GUILD_TEXT,

        @SerializedName("1")
        DM,

        @SerializedName("2")
        GUILD_VOICE,

        @SerializedName("3")
        GROUP_DM,

        @SerializedName("4")
        GUILD_CATEGORY
    }
}

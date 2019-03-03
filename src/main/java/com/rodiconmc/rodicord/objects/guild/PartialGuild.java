package com.rodiconmc.rodicord.objects.guild;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.Bot;
import com.rodiconmc.rodicord.DiscordException;
import com.rodiconmc.rodicord.objects.channel.*;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Represents a {@link Guild} that does not have all it's details.
 */
public class PartialGuild {
    private String id;
    private String name;
    private String icon;
    private boolean owner;
    private int permissions;

    PartialGuild() {
        //no-args constructor
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isOwner() {
        return owner;
    }

    public int getPermissions() {
        return permissions; //TODO move to permissions object
    }

    /**
     * Gets the full {@link Guild} object. Useful if this is a {@link PartialGuild} instance.
     * @param bot The bot to get the guild from
     * @return The full guild.
     */
    public Guild getFull(@NotNull Bot bot) {
        return bot.getGuild(id);
    }

    private Channel createChannel(JsonObject data, Bot bot) {
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/guilds/%s/channels", this.id)))
                .POST(HttpRequest.BodyPublishers.ofString(bot.gson.toJson(data)))
                .build();
        HttpResponse<String> response = bot.makeRequest(request);
        Channel.Type channelType = bot.gson.fromJson(bot.gson.fromJson(response.body(), JsonObject.class)
                .getAsJsonPrimitive("type"), Channel.Type.class);
        switch (channelType) {
            case DM:
                return bot.gson.fromJson(response.body(), DmChannel.class);
            case GROUP_DM:
                return bot.gson.fromJson(response.body(), GroupDmChannel.class);
            case GUILD_TEXT:
                return bot.gson.fromJson(response.body(), GuildChannel.class);
            case GUILD_VOICE:
                return bot.gson.fromJson(response.body(), VoiceChannel.class);
            case GUILD_CATEGORY:
                return bot.gson.fromJson(response.body(), CategoryChannel.class);
            default:
                throw new DiscordException(80003, "Unknown channel type");
        }
    }

    /**
     * Creates a channel in the guild.
     * @param name The name for the new channel
     * @param type The type of the new channel
     * @param bot The bot to create the channel as
     * @return The new channel
     * @see <a href="https://discordapp.com/developers/docs/resources/guild#create-guild-channel">
     *     Discord Create Guild Channel</a>
     */
    public Channel createChannel(@NotNull String name, @NotNull Channel.Type type, @NotNull Bot bot) {
        JsonObject data = new JsonObject();
        data.addProperty("name", name);
        data.addProperty("type", type.ordinal());
        return createChannel(data, bot);
    }

    /**
     * Creates a channel in the guild.
     * @param name The name for the new channel
     * @param type The type of the new channel
     * @param parentId The snowflake Id of the parent channel
     * @param bot The bot to create the channel as
     * @return The new channel
     * @see <a href="https://discordapp.com/developers/docs/resources/guild#create-guild-channel">
     *     Discord Create Guild Channel</a>
     */
    public Channel createChannel(@NotNull String name, @NotNull Channel.Type type, @NotNull String parentId, @NotNull Bot bot) {
        JsonObject data = new JsonObject();
        data.addProperty("name", name);
        data.addProperty("type", type.ordinal());
        data.addProperty("parent_id", parentId);
        return createChannel(data, bot);
    }
}

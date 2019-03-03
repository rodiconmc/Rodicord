package com.rodiconmc.rodicord;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.objects.User;
import com.rodiconmc.rodicord.objects.channel.*;
import com.rodiconmc.rodicord.objects.guild.Guild;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for manipulating Bot endpoints. For bots, see the {@link Bearer} class.
 */
public class Bot extends Agent {
    Bot(BotToken token) {
        super(token);
    }

    /**
     * Gets the discord user object for a given user ID.
     * @param id Snowflake id of the requested user
     * @return {@link User} object from given id
     * @see <a href="https://discordapp.com/developers/docs/resources/user#get-user">Discord Get User</a>
     */
    public User getUser(@NotNull String id) throws DiscordException {
        HttpRequest request = getHttpBuilder().uri(URI.create(baseUrl + String.format("/users/%s", id))).build();
        HttpResponse<String> response = makeRequest(request);
        return gson.fromJson(response.body(), User.class);
    }

    /**
     * Modify the bot's user account settings. Returns a {@link User} object on success.
     * All parameters are optional, and can be omitted by providing a null pointer
     * @param username New Username
     * @param avatarData New <a href="https://discordapp.com/developers/docs/resources/user#avatar-data">avatar data</a>
     * @return New {@link User} object
     * @see <a href="https://discordapp.com/developers/docs/resources/user#modify-current-user">Discord Modify Current User</a>
     */
    public User modifySelf(@Nullable String username, @Nullable String avatarData) throws DiscordException {
        Map<String, String> data = new HashMap<>();
        if (username != null) data.put("username", username);
        if (avatarData != null) data.put("avatar", avatarData);
        String jsonData = gson.toJson(data);
        HttpRequest request = getHttpBuilder()
                .uri(URI.create(baseUrl + "/users/@me"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonData))
                .build();
        HttpResponse<String> response = makeRequest(request);
        return gson.fromJson(response.body(), User.class);
    }

    /**
     * Create a new DM channel with a user. Returns a {@link DmChannel} object.
     * @param recipientId the snowflake id of the user to DM with
     * @return the {@link DmChannel}
     * @see <a href="https://discordapp.com/developers/docs/resources/user#create-dm">Discord Create DM</a>
     */
    public DmChannel createDm(@NotNull String recipientId) throws DiscordException {
        JsonObject data = new JsonObject();
        data.addProperty("recipient_id", recipientId);
        HttpRequest request = getHttpBuilder().uri(URI.create(baseUrl + "/users/@me/channels"))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(data)))
                .build();
        HttpResponse<String> response = makeRequest(request);
        return gson.fromJson(response.body(), DmChannel.class);
    }

    /**
     * Get a channel by ID. Returns a subclass of the {@link Channel} object
     * @param id Snowflake id of the channel
     * @return A subclass of the {@link Channel} object
     */
    public Channel getChannel(@NotNull String id) {
        HttpRequest request = getHttpBuilder().uri(URI.create(baseUrl + String.format("/channels/%s", id))).build();
        HttpResponse<String> response = makeRequest(request);
        Channel.Type channelType = gson.fromJson(gson.fromJson(response.body(), JsonObject.class)
                .getAsJsonPrimitive("type"), Channel.Type.class);
        switch (channelType) {
            case DM:
                return gson.fromJson(response.body(), DmChannel.class);
            case GROUP_DM:
                return gson.fromJson(response.body(), GroupDmChannel.class);
            case GUILD_TEXT:
                return gson.fromJson(response.body(), GuildChannel.class);
            case GUILD_VOICE:
                return gson.fromJson(response.body(), VoiceChannel.class);
            case GUILD_CATEGORY:
                return gson.fromJson(response.body(), CategoryChannel.class);
            default:
                throw new DiscordException(80003, "Unknown channel type");
        }
    }

    /**
     * Get a Guild by ID. Returns a {@link Guild} object
     * @param id Snowflake id of the guild
     * @return A {@link Guild} object
     */
    public Guild getGuild(@NotNull String id) {
        HttpRequest request = getHttpBuilder().uri(URI.create(baseUrl + String.format("/guilds/%s", id))).build();
        HttpResponse<String> response = makeRequest(request);
        return gson.fromJson(response.body(), Guild.class);
    }
}

package com.rodiconmc.rodicord.objects.guild;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.Bot;
import com.rodiconmc.rodicord.objects.User;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpRequest;

/**
 * An extension of the {@link com.rodiconmc.rodicord.objects.User} object which includes Guild-Specific details
 */
public class GuildUser {
    private User user;
    private String nick;
    private String[] roles;
    private String joined_at;
    private boolean deaf;
    private boolean mute;
    private String guild_id;

    private GuildUser() {
        //no-args constructor
    }

    public User getUser() {
        return user;
    }

    public String getNick() {
        return nick;
    }

    public String[] getRoles() {
        return roles;
    }

    public String getJoinedAt() {
        return joined_at;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public boolean isMute() {
        return mute;
    }

    public String getGuildId() {
        return guild_id;
    }

    /**
     * Moves the user to a different voice channel, as long as they are in one already.
     * @param channelId The Snowflake id of the channel to move them to
     * @param bot The bot to perform the action as
     */
    public void moveVoice(@NotNull String channelId, @NotNull Bot bot) {
        JsonObject data = new JsonObject();
        data.addProperty("channel_id", channelId);
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/guilds/%s/members/%s",
                this.getGuildId(), this.getUser().getId())))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(bot.gson.toJson(data)))
                .build();
        bot.makeRequest(request);
    }
}

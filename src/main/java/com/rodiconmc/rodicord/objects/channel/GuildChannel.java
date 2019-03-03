package com.rodiconmc.rodicord.objects.channel;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.Bot;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpRequest;

public class GuildChannel extends Channel implements Messageable, Nameable {
    private String guild_id;
    private String name;
    private int position;
    private ChannelOverwrite[] permission_overwrites;
    private int rate_limit_per_user;
    private boolean nsfw;
    private String topic;
    private String last_message_id;
    private String parent_id;

    private GuildChannel() {
        //no-args constructor
    }

    public String getGuildId() {
        return guild_id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public ChannelOverwrite[] getPermissionOverwrites() {
        return permission_overwrites;
    }

    public int getRateLimitPerUser() {
        return rate_limit_per_user;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public String getTopic() {
        return topic;
    }

    public String getLastMessageId() {
        return last_message_id;
    }

    public String getParentId() {
        return parent_id;
    }

    @Override
    public void sendTextMessage(@NotNull String message, @NotNull Bot bot) {
        JsonObject data = new JsonObject();
        data.addProperty("content", message);
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/channels/%s/messages", this.getId())))
                .POST(HttpRequest.BodyPublishers.ofString(bot.gson.toJson(data)))
                .build();
        bot.makeRequest(request);
    }

    @Override
    public Channel setName(@NotNull String name, @NotNull Bot bot) {
        return modifyChannel("name", name, bot);
    }
}

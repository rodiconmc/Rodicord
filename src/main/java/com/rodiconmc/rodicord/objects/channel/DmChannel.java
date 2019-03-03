package com.rodiconmc.rodicord.objects.channel;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.Bot;
import com.rodiconmc.rodicord.objects.User;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpRequest;

public class DmChannel extends Channel implements Messageable, Nameable {
    private String last_message_id;
    private User[] recipients;

    private DmChannel() {
        //no-args constructor
    }

    public String getLastMessageId() {
        return last_message_id;
    }

    public User[] getRecipients() {
        return recipients;
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

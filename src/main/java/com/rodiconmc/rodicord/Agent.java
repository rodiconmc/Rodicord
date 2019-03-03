package com.rodiconmc.rodicord;


import com.google.gson.Gson;
import com.rodiconmc.rodicord.objects.User;
import com.rodiconmc.rodicord.objects.guild.PartialGuild;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * An abstract class representing both {@link Bearer} and {@link Bot} objects.
 */
abstract class Agent {
    public static String baseUrl = "https://discordapp.com/api/v6";
    final Token token;
    final HttpClient client;
    public final Gson gson = new Gson();

    Agent(Token token) {
        this.token = token;
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public HttpRequest.Builder getHttpBuilder() {
        return HttpRequest.newBuilder()
                .header("Agent-Agent", "Rodicord (https://github.com/rodiconmc/Rodicord, 1.0.0)")
                .header("Content-Type", "application/json")
                .header("Authorization", token.getTokenType() + " " + token.getAccessToken());
    }

    public HttpResponse<String> makeRequest(HttpRequest request) throws DiscordException {
        try {
            HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
            if (Integer.parseInt(Integer.toString(response.statusCode()).substring(0, 1)) == 4) {
                System.out.println(response.body());
                throw gson.fromJson(response.body(), DiscordException.class);
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            throw new DiscordException(80001, "IOException thrown when submitting an http request.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new DiscordException(80002, "InterruptedException thrown when submitting an http request.");
        }
    }

    /**
     * Gets the discord user object for the current agent.
     * @return Current {@link User} object
     * @see <a href="https://discordapp.com/developers/docs/resources/user#get-current-user">Discord Get Current User</a>
     */
    public User getSelf() throws DiscordException {
        HttpRequest request = getHttpBuilder().uri(URI.create(baseUrl + "/users/@me")).build();
        HttpResponse<String> response = makeRequest(request);
        return gson.fromJson(response.body(), User.class);
    }

    /**
     * Gets an array of {@link PartialGuild} objects the current agent is a member of.
     * @return Array of {@link PartialGuild} which the current agent is a part of.
     */
    public PartialGuild[] getOwnGuilds() throws DiscordException {
        HttpRequest request = getHttpBuilder().uri(URI.create(baseUrl + "/users/@me/guilds")).build();
        HttpResponse<String> response = makeRequest(request);
        return gson.fromJson(response.body(), PartialGuild[].class);
    }

}

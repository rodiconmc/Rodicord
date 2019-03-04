package com.rodiconmc.rodicord;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A class for performing actions on behalf of an oauth2 application.
 */
public class Application {
    public final String clientId;
    final String clientSecret;
    private final HttpClient client;
    public final Gson gson = new Gson();

    public Application(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public HttpRequest.Builder getHttpBuilder() {
        return HttpRequest.newBuilder()
                .header("Agent-Agent", "Rodicord (https://github.com/rodiconmc/Rodicord, 1.0.0)")
                .header("Content-Type", "application/x-www-form-urlencoded");
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
}

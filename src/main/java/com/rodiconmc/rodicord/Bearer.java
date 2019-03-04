package com.rodiconmc.rodicord;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.objects.User;
import com.rodiconmc.rodicord.objects.guild.PartialGuild;
import com.rodiconmc.rodicord.util.QueryUtil;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for manipulating Bearer/User endpoints. For bots, see the {@link Bot} class.
 */
public class Bearer extends Agent {
    private Application application = null;

    /**
     * Basic constructor. Subsequent actions may fail if token has expired
     * @param token Token to use
     */
    public Bearer(BearerToken token) {
        super(token);
    }

    /**
     * Recommended constructor. Will renew token using the provided application if necessary.
     * @param token Token to use
     * @param application Application to renew token with. Must have the same credentials as the application that
     *                    generated the access/refresh tokens in the first place.
     */
    public Bearer(BearerToken token, Application application) {
        super(token);
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    /**
     * Gets a new Access Token using the refresh token exchange
     * @see <a href="https://discordapp.com/developers/docs/topics/oauth2#authorization-code-grant-refresh-token-exchange-example">
     *     Discord Refresh Token Exchange</a>
     */
    public void refreshToken() {
        if (application == null) return;
        Map<String, String> data = new HashMap<>();
        data.put("client_id", application.clientId);
        data.put("client_secret", application.clientSecret);
        data.put("grant_type", "refresh_token");
        data.put("refresh_token", ((BearerToken) token).getRefreshToken());
        data.put("scope", ((BearerToken) token).getScope());
        HttpRequest request = application.getHttpBuilder()
                .uri(URI.create(Bearer.baseUrl + "/oauth2/token"))
                .POST(HttpRequest.BodyPublishers.ofString(QueryUtil.urlEncodeUTF8(data)))
                .build();
        HttpResponse<String> response = application.makeRequest(request);
        JsonObject tokenData = gson.fromJson(response.body(), JsonObject.class);
        ((BearerToken) token).setAccessToken(tokenData.get("access_token").getAsString(),
                new Date(System.currentTimeMillis() + (tokenData.get("expires_in").getAsLong() * 1000)));

    }

    /**
     * Performs {@link Bearer#refreshToken()} if the access token is expired
     */
    public void refreshIfNecessary() {
        if (System.currentTimeMillis() > ((BearerToken) token).getExpiresAt().getTime()) {
            refreshToken();
        }
    }

    @Override
    public User getSelf() throws DiscordException {
        refreshIfNecessary();
        return super.getSelf();
    }

    @Override
    public PartialGuild[] getOwnGuilds() throws DiscordException {
        refreshIfNecessary();
        return super.getOwnGuilds();
    }
}

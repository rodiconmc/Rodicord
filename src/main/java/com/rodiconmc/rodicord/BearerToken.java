package com.rodiconmc.rodicord;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents a set of an Access Token, Refresh Token, and an expiry date for a Discord Agent. (For bots see {@link BotToken})
 */
public class BearerToken extends Token {

    private String accessToken;
    private String refreshToken;
    private Date expiresAt;
    private Set<Consumer<BearerToken>> changeListeners = new HashSet<>();

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param expiresAt The date object representing when the Access Token expires
     */
    public BearerToken(String accessToken, String refreshToken, Date expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param expiresAt The epoch time for when the Access Token expires (Millliseconds since January 1, 1970, 00:00:00 GMT)
     */
    public BearerToken(String accessToken, String refreshToken, long expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = new Date(expiresAt);
    }

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param expiresAt The date object representing when the Access Token expires
     * @param changeListeners Consumers which should be run when any token data is updated.
     */
    public BearerToken(String accessToken, String refreshToken, Date expiresAt, Consumer<BearerToken>... changeListeners) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.changeListeners.addAll(Arrays.asList(changeListeners));
    }

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param expiresAt The epoch time for when the Access Token expires (Milliseconds since January 1, 1970, 00:00:00 GMT)
     * @param changeListeners Consumers which should be run when any token data is updated
     */
    public BearerToken(String accessToken, String refreshToken, long expiresAt, Consumer<BearerToken>... changeListeners) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = new Date(expiresAt);
        this.changeListeners.addAll(Arrays.asList(changeListeners));
    }

    /**
     * @param changeListener Consumer which should be called whenever token data is updated
     */
    public void addChangeListener(Consumer<BearerToken> changeListener) {
        changeListeners.add(changeListener);
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getTokenType() {
        return "Bearer";
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    void callChangeListeners() {
        for (Consumer<BearerToken> changeListener : changeListeners) {
            changeListener.accept(this);
        }
    }

}

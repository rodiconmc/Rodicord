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
    private String scope;
    private Date expiresAt;
    private Set<Consumer<BearerToken>> changeListeners = new HashSet<>();

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param scope The scopes requested when the token was made, space-delimited
     * @param expiresAt The date object representing when the Access Token expires
     */
    public BearerToken(String accessToken, String refreshToken, String scope, Date expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresAt = expiresAt;
    }

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param scope The scopes requested when the token was made, space-delimited
     * @param expiresAt The epoch time for when the Access Token expires (Millliseconds since January 1, 1970, 00:00:00 GMT)
     */
    public BearerToken(String accessToken, String refreshToken, String scope, long expiresAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresAt = new Date(expiresAt);
    }

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param scope The scopes requested when the token was made, space-delimited
     * @param expiresAt The date object representing when the Access Token expires
     * @param changeListeners Consumers which should be run when any token data is updated.
     */
    public BearerToken(String accessToken, String refreshToken, String scope, Date expiresAt, Consumer<BearerToken>... changeListeners) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresAt = expiresAt;
        this.changeListeners.addAll(Arrays.asList(changeListeners));
    }

    /**
     * @param accessToken The Access Token given by the Discord Oauth2 API
     * @param refreshToken The Refresh Token given by the Discord Oauth2 API
     * @param scope The scopes requested when the token was made, space-delimited
     * @param expiresAt The epoch time for when the Access Token expires (Milliseconds since January 1, 1970, 00:00:00 GMT)
     * @param changeListeners Consumers which should be run when any token data is updated
     */
    public BearerToken(String accessToken, String refreshToken, String scope, long expiresAt, Consumer<BearerToken>... changeListeners) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
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

    public String getScope() {
        return scope;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    void setAccessToken(String accessToken, Date expiresAt) {
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        callChangeListeners();
    }

    void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        callChangeListeners();
    }

    private void callChangeListeners() {
        for (Consumer<BearerToken> changeListener : changeListeners) {
            changeListener.accept(this);
        }
    }

}

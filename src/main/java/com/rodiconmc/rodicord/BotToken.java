package com.rodiconmc.rodicord;

/**
 * Represents an Access Token for a Discord Bot. (For users see {@link BearerToken})
 */
public class BotToken extends Token {

    private String accessToken;

    /**
     * @param accessToken The Access Token given by the Discord Bot interface
     */
    public BotToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getTokenType() {
        return "Bot";
    }
}

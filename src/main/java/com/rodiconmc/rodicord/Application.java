package com.rodiconmc.rodicord;

/**
 * A class for performing actions on behalf of an oauth2 application.
 */
public class Application {
    private String clientId;
    private String clientSecret;

    public Application(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}

package com.rodiconmc.rodicord;

/**
 * An abstract class representing both {@link BearerToken} and {@link BotToken} objects.
 */
abstract class Token {
    abstract public String getAccessToken();
    abstract public String getTokenType();
}

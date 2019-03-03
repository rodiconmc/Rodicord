package com.rodiconmc.rodicord;

/**
 * Represents an error thown either by the discord api, or by the Rodicon api.
 * Errors thrown by the discord api will have error codes matching the ones listed <a href="https://discordapp.com/developers/docs/topics/opcodes-and-status-codes#json">here</a>.
 * Errors thrown by the Rodicon api will have error codes in the list below:<br>
 * 0 - Unknown exception. Check logs for more details.
 * 80001 - IOException thrown when submitting an http request.<br>
 * 80002 - InterruptedException thrown when submitting an http request.<br>
 * 80003 - Unknown channel type<br>
 */
public class DiscordException extends RuntimeException {
    private int code;
    private String message;

    public DiscordException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

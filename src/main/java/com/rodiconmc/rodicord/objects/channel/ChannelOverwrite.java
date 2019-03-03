package com.rodiconmc.rodicord.objects.channel;

import com.google.gson.annotations.SerializedName;

/**
 * Represents explicit permission overwrites for members and roles
 * @see <a href="https://discordapp.com/developers/docs/resources/channel#overwrite-object">Discord Overwrite Object</a>
 */
public class ChannelOverwrite {
    private String id;
    private Type type;
    private int allow;
    private int deny;

    private ChannelOverwrite() {
        //no-args constructor
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getAllow() {
        return allow; //TODO convert to permission object
    }

    public int getDeny() {
        return deny; //TODO convert to permission object
    }

    public enum Type {
        @SerializedName("role")
        ROLE,

        @SerializedName("member")
        MEMBER
    }
}

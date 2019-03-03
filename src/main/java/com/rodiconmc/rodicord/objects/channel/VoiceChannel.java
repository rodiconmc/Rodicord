package com.rodiconmc.rodicord.objects.channel;

import com.rodiconmc.rodicord.Bot;
import org.jetbrains.annotations.NotNull;

public class VoiceChannel extends Channel implements Nameable {
    private String guild_id;
    private String name;
    private boolean nsfw;
    private int position;
    private ChannelOverwrite[] permission_overwrites;
    private int bitrate;
    private int user_limit;
    private String parent_id;

    private VoiceChannel() {
        //no-args constructor
    }

    public String getGuildId() {
        return guild_id;
    }

    public String getName() {
        return name;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public int getPosition() {
        return position;
    }

    public ChannelOverwrite[] getPermissionOverwrites() {
        return permission_overwrites;
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getUserLimit() {
        return user_limit;
    }

    public String getParentId() {
        return parent_id;
    }

    @Override
    public Channel setName(@NotNull String name, @NotNull Bot bot) {
        return modifyChannel("name", name, bot);
    }
}

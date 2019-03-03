package com.rodiconmc.rodicord.objects.channel;

import com.rodiconmc.rodicord.Bot;
import org.jetbrains.annotations.NotNull;

public class CategoryChannel extends Channel implements Nameable {
    private ChannelOverwrite[] permission_overwrites;
    private String name;
    private String parent_id;
    private boolean nsfw;
    private int position;
    private String guild_id;

    private CategoryChannel() {
        //no-args constructor
    }

    public ChannelOverwrite[] getPermissionOverwrites() {
        return permission_overwrites;
    }

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parent_id;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public int getPosition() {
        return position;
    }

    public String getGuildId() {
        return guild_id;
    }

    @Override
    public Channel setName(@NotNull String name, @NotNull Bot bot) {
        return modifyChannel("name", name, bot);
    }
}

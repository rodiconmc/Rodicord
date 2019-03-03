package com.rodiconmc.rodicord.objects.channel;

import com.rodiconmc.rodicord.Bot;
import com.rodiconmc.rodicord.objects.User;
import org.jetbrains.annotations.NotNull;

public class GroupDmChannel extends Channel implements Nameable {
    private String name;
    private String icon;
    private User[] recipients;
    private String last_message_id;
    private String owner_id;

    private GroupDmChannel() {
        //no-args constructor
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public User[] getRecipients() {
        return recipients;
    }

    public String getLastMessageId() {
        return last_message_id;
    }

    public String getOwnerId() {
        return owner_id;
    }

    @Override
    public Channel setName(@NotNull String name, @NotNull Bot bot) {
        return modifyChannel("name", name, bot);
    }
}

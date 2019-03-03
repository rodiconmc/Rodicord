package com.rodiconmc.rodicord.objects.channel;

import com.rodiconmc.rodicord.Bot;
import org.jetbrains.annotations.NotNull;

public interface Messageable {
    void sendTextMessage(@NotNull String message, @NotNull Bot bot);
}

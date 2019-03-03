package com.rodiconmc.rodicord.objects.channel;

import com.rodiconmc.rodicord.Bot;
import org.jetbrains.annotations.NotNull;

public interface Nameable {
    Channel setName(@NotNull String name, @NotNull Bot bot);
}

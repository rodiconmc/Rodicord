package com.rodiconmc.rodicord.objects.guild;

import com.google.gson.JsonObject;
import com.rodiconmc.rodicord.Bot;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Represents a Discord Guild. Guilds in Discord represent an isolated collection of users and channels, and are often
 * referred to as "servers" in the UI.
 * @see <a href="https://discordapp.com/developers/docs/resources/guild#guild-resource">Discord Guild Resource</a>
 */
public class Guild extends PartialGuild {
    private String splash;
    private String owner_id;
    private String region;
    private String afk_channel_id;
    private int afk_timeout;
    private boolean embed_enabled;
    private String embed_channel_id;
    private int verification_level;
    private int default_message_notifications;
    private int explicit_content_filter;
    private Role[] roles;
    //private Emoji[] emojis; TODO add emojis
    private int mfa_level;
    private String application_id;

    private Guild() {
        //no-args constructor
    }

    public String getSplash() {
        return splash;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getRegion() {
        return region;
    }

    public String getAfk_channel_id() {
        return afk_channel_id;
    }

    public int getAfk_timeout() {
        return afk_timeout;
    }

    public boolean isEmbed_enabled() {
        return embed_enabled;
    }

    public String getEmbed_channel_id() {
        return embed_channel_id;
    }

    public int getVerification_level() {
        return verification_level;
    }

    public int getDefault_message_notifications() {
        return default_message_notifications;
    }

    public int getExplicit_content_filter() {
        return explicit_content_filter;
    }

    public Role[] getRoles() {
        return roles;
    }

    public int getMfa_level() {
        return mfa_level;
    }

    public String getApplication_id() {
        return application_id;
    }

    /**
     * Returns a {@link GuildUser} object for the specified user id.
     * @param id Snowflake id of the user
     * @param bot Bot to get the user as
     * @return The GuildUser
     * @see <a href="https://discordapp.com/developers/docs/resources/guild#get-guild-member">Discord Get Guild Member</a>
     */
    public GuildUser getGuildUser(@NotNull String id, @NotNull Bot bot) {
        HttpRequest request = bot.getHttpBuilder().uri(URI.create(Bot.baseUrl + String.format("/guilds/%s/members/%s", this.getId(), id)))
                .build();
        HttpResponse<String> response = bot.makeRequest(request);
        JsonObject data = bot.gson.fromJson(response.body(), JsonObject.class);
        data.addProperty("guild_id", this.getId());
        return bot.gson.fromJson(bot.gson.toJson(data), GuildUser.class);
    }
}

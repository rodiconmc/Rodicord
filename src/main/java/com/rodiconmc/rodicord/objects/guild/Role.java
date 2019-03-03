package com.rodiconmc.rodicord.objects.guild;

/**
 * Represents a role in a guild. Roles represent a set of permissions attached to a group of users. Roles have unique
 * names, colors, and can be "pinned" to the side bar, causing their members to be listed separately. Roles are unique
 * per guild, and can have separate permission profiles for the global context (guild) and channel context. The
 * \@everyone role has the same ID as the guild it belongs to.
 * @see <a href="https://discordapp.com/developers/docs/topics/permissions#role-object">Discord Role Object</a>
 */
public class Role {
    private String id;
    private String name;
    private int color;
    private boolean hoist;
    private int position;
    private int permissions;
    private boolean managed;
    private boolean mentionable;

    private Role() {
        //no-args constructor
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public boolean isHoist() {
        return hoist;
    }

    public int getPosition() {
        return position;
    }

    public int getPermissions() {
        return permissions; //TODO Change to permissions object
    }

    public boolean isManaged() {
        return managed;
    }

    public boolean isMentionable() {
        return mentionable;
    }
}

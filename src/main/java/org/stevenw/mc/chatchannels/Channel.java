package org.stevenw.mc.chatchannels;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class Channel {
    public abstract String getReceivePermission();
    public abstract String getSendPermission();
    public abstract String getMessageFormat();
    public abstract String getName();
    public abstract List<String> getAliases();
    public abstract String getDisplayName();
    public abstract int sendMessage(Player sender, String message);
    public abstract boolean toggleable();
}

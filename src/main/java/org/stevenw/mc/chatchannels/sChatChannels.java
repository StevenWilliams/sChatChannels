package org.stevenw.mc.chatchannels;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.stevenw.mc.chatchannels.listeners.ChatListener;
import org.stevenw.mc.chatchannels.listeners.CommandListener;

public class sChatChannels extends JavaPlugin {
    private ChannelManager channelManager;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        channelManager = new ChannelManager(this);
        channelManager.loadPermChannels();
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }
    @Override
    public void onDisable() {

    }
    public ChannelManager getChannelManager() {
        return channelManager;
    }
    public String messageFormatter(Channel channel, Player player, String message, String chatformat) {
        //Replace with ChatReplacer object, and ChatReplacer manager
        chatformat = ChatColor.translateAlternateColorCodes('&', chatformat);
        chatformat = chatformat.replace("{DISPLAYNAME}", player.getDisplayName());
        chatformat = chatformat.replace("{MESSAGE}", message);
        chatformat = chatformat.replace("{CHANNEL}", channel.getDisplayName());
        chatformat = chatformat.replace("{USERNAME}", player.getName());
        chatformat = ChatColor.translateAlternateColorCodes('&', chatformat);
        return chatformat;

    }
}

package org.stevenw.mc.chatchannels;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.stevenw.mc.chatchannels.listeners.ChatListener;
import org.stevenw.mc.chatchannels.listeners.CommandListener;
import org.stevenw.mc.chatchannels.listeners.MessageListener;

public class sChatChannels extends JavaPlugin {
    private ChannelManager channelManager;
    private RedisManager redisManager =null;

    public static sChatChannels getPlugin() {
        return plugin;
    }

    public static sChatChannels plugin;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        channelManager = new ChannelManager(this);
        channelManager.loadPermChannels();
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new MessageListener(this), this);
        plugin = this;
        if(this.getConfig().getBoolean("use-redis", false)) {
            this.redisManager  = RedisManager.getInstance();
            redisManager.subscribe();
        }
    }

    @Override
    public void onDisable() {
        plugin=null;
    }
    public String getServerName(){
        return this.getConfig().getString("server");
    }
    public ChannelManager getChannelManager() {
        return channelManager;
    }

    public String messageFormatter(Channel channel, Player player, String message, String chatFormat) {
        //Replace with ChatReplacer object, and ChatReplacer manager
        chatFormat = ChatColor.translateAlternateColorCodes('&', chatFormat);
        chatFormat = chatFormat.replace("{DISPLAYNAME}", player.getDisplayName());
        chatFormat = chatFormat.replace("{MESSAGE}", message);
        chatFormat = chatFormat.replace("{CHANNEL}", channel.getDisplayName());
        chatFormat = chatFormat.replace("{USERNAME}", player.getName());
        chatFormat = ChatColor.translateAlternateColorCodes('&', chatFormat);
        return chatFormat;

    }

    public RedisManager getRedisManager() {
        return redisManager;
    }
}

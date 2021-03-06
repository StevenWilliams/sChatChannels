package org.stevenw.mc.chatchannels;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class PermanentChannel extends Channel{
    private final String name;
    private final sChatChannels plugin;
    private final ConfigurationSection channelConfig;

    public PermanentChannel(sChatChannels plugin, String name) {
        this.name = name;
        this.plugin = plugin;
        channelConfig = plugin.getConfig().getConfigurationSection("permanent-channels." + name);
    }

    @Override
    public String getReceivePermission() {
        return "channels.channel."  + name + ".receive";
    }

    @Override
    public String getSendPermission() {
        return "channels.channel."  + name + ".send";
    }

    @Override
    public String getMessageFormat() {
        return channelConfig.getString("format");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getAliases() {
        return channelConfig.getStringList("aliases");
    }

    @Override
    public String getDisplayName() {
        if(this.channelConfig.isSet("display-name")) {
            return this.channelConfig.getString("display-name");
        }
        return this.getName();
    }

    @Override
    public int sendMessage(Player sender, String message) {

        String formattedMessage = plugin.messageFormatter(this, sender, message, getMessageFormat());
        Message message1 = new Message(plugin.getServerName(), this.getReceivePermission(), formattedMessage, this.name, sender);
        MessageEvent event = (new MessageEvent(message1));
        if(plugin.getRedisManager() != null &&channelConfig.getBoolean("global", false)) {
            RedisManager redis = plugin.getRedisManager();
            redis.publish("chatchannels", redis.toJson(message1));
        } else {
            //needs to be called only if not using redis since redis subscribe will already call it
            Bukkit.getPluginManager().callEvent(event);
        }
        int players = event.getReceivedByCount();
        if(players == 0)
        {
            sender.sendMessage(plugin.messageFormatter(this, sender, message, plugin.getConfig().getString("none-online")));
        } else {
            if (!sender.hasPermission(this.getReceivePermission())) {
                sender.sendMessage(plugin.messageFormatter(this, sender, message, plugin.getConfig().getString("msg-sent")));
            }
        }
        return players;
    }

    public boolean global() {
        return this.channelConfig.getBoolean("toggleable");
    }

    @Override
    public boolean toggleable() {
        return !this.channelConfig.isSet("toggleable") || this.channelConfig.getBoolean("toggleable");
    }
}

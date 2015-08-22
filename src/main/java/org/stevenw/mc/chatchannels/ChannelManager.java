package org.stevenw.mc.chatchannels;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class ChannelManager {
    private final sChatChannels plugin;
    private final ArrayList<Channel> channels = new ArrayList<>();
    private final HashMap<UUID, String> toggledChannel = new HashMap<>();
    public ChannelManager(sChatChannels plugin)
    {
        this.plugin = plugin;
    }
    public boolean addChannel(Channel channel) {
       return channels.add(channel);
    }
    public void loadPermChannels() {
        Set<String> permChannelNames = plugin.getConfig().getConfigurationSection("permanent-channels").getKeys(false);
        for(String permChannelName : permChannelNames)
        {
            PermanentChannel permChannel = new PermanentChannel(plugin, permChannelName);
            this.addChannel(permChannel);
        }
    }
    public Channel getActiveChannel(UUID player)
    {
         if(toggledChannel.containsKey(player))
         {
             String channelName = toggledChannel.get(player);
             if(this.get(channelName) != null)
             {
                 return this.get(channelName);
             }
         }
        return null;
    }
    public boolean setActiveChannel(UUID uuid, String channelname) {
        Player player = plugin.getServer().getPlayer(uuid);
        if(this.exists(channelname)) {
            Channel channel = this.get(channelname);
            this.toggledChannel.put(uuid, channelname);
            player.sendMessage(plugin.messageFormatter(channel, player, "", plugin.getConfig().getString("toggled-on")));
            return true;
        }

        return false;
    }
    public void unsetActiveChannel(UUID uuid)
    {
        Player player = plugin.getServer().getPlayer(uuid);
        if(this.getActiveChannel(uuid) != null) {
            Channel channel = this.getActiveChannel(uuid);
            this.toggledChannel.remove(uuid);
            player.sendMessage(plugin.messageFormatter(channel, player, "", plugin.getConfig().getString("toggled-off")));
        }
    }
    public boolean exists(String channelName) {
        for(Channel channel : channels)
        {
            if (channel.getName().equalsIgnoreCase(channelName))
            {
                return true;
            }
            for(String alias : channel.getAliases())
            {
                if(alias.equalsIgnoreCase(channelName))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public Channel get(String channelName) {
        for(Channel channel : channels)
        {
            if (channel.getName().equalsIgnoreCase(channelName))
            {
                return channel;
            }
            for(String alias : channel.getAliases())
            {
                if(alias.equalsIgnoreCase(channelName))
                {
                    return channel;
                }
            }
        }
        return null;
    }

}

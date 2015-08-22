package org.stevenw.mc.chatchannels.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.stevenw.mc.chatchannels.Channel;
import org.stevenw.mc.chatchannels.sChatChannels;

public class ChatListener implements Listener {
    private final sChatChannels plugin;
    public ChatListener(sChatChannels plugin)
    {
     this.plugin = plugin;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        if(plugin.getChannelManager().getActiveChannel(player.getUniqueId()) != null)
        {
            Channel channel = plugin.getChannelManager().getActiveChannel(player.getUniqueId());
            if(!event.getPlayer().hasPermission(channel.getSendPermission()))
            {
                player.sendMessage(plugin.messageFormatter(channel, player, "", plugin.getConfig().getString("no-access")));
                return;
            }
            channel.sendMessage(player, event.getMessage());
            event.setCancelled(true);
        }
    }
}

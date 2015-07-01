package org.stevenw.mc.chatchannels.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.stevenw.mc.chatchannels.Channel;
import org.stevenw.mc.chatchannels.sChatChannels;

public class CommandListener implements Listener {
    private sChatChannels plugin;
    public CommandListener(sChatChannels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String typed = event.getMessage().replaceFirst("/", "");
        String typedSplit[] = typed.split(" ", 2);
        String command = typedSplit[0];
        Player player = event.getPlayer();
        if(plugin.getChannelManager().exists(command))
        {
            Channel channel = plugin.getChannelManager().get(command);
            if(!event.getPlayer().hasPermission(channel.getSendPermission()))
            {
                player.sendMessage(plugin.messageFormatter(channel, player, "", plugin.getConfig().getString("no-access")));
            } else {
                if (typedSplit.length < 2) {
                    if (channel.equals(plugin.getChannelManager().getActiveChannel(event.getPlayer().getUniqueId()))) {
                        plugin.getChannelManager().unsetActiveChannel(event.getPlayer().getUniqueId());
                    } else {
                        if(channel.toggleable()) {
                            plugin.getChannelManager().setActiveChannel(event.getPlayer().getUniqueId(), channel.getName());
                        } else {
                            player.sendMessage(plugin.messageFormatter(channel, player, "", plugin.getConfig().getString("no-msg")));
                        }
                    }
                } else {
                    channel.sendMessage(event.getPlayer(), typedSplit[1]);
                }
            }
            event.setCancelled(true);
        }
    }
}

package org.stevenw.mc.chatchannels.listeners;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.stevenw.mc.chatchannels.Message;
import org.stevenw.mc.chatchannels.MessageEvent;
import org.stevenw.mc.chatchannels.sChatChannels;

public class MessageListener implements Listener {
    private sChatChannels plugin;

    public MessageListener(sChatChannels plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onMessageSend(MessageEvent e) {
        Message message = e.getMessage();
        int players =0;
        OfflinePlayer sender  = message.getSender();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.hasPermission(message.getReadPerm())) {

                player.sendMessage(message.getMessage());
            }
        }
        e.setReceivedByCount(players);
    }
}

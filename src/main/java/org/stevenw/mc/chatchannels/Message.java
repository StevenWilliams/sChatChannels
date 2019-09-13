package org.stevenw.mc.chatchannels;

import org.bukkit.OfflinePlayer;

public class Message {
    private String serverName;
    private String readPerm;
    private String message;
    private String channel;
    private transient OfflinePlayer sender;

    public Message(String serverName, String readPerm, String message, String channel, OfflinePlayer sender ) {
        this.serverName = serverName;
        this.readPerm = readPerm;
        this.message = message;
        this.channel = channel;
        this.sender = sender;
    }

    public String getReadPerm() {
        return readPerm;
    }

    public String getMessage() {
        return message;
    }

    public String getChannel() {
        return channel;
    }

    public OfflinePlayer getSender() {
        return sender;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setReadPerm(String readPerm) {
        this.readPerm = readPerm;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setSender(OfflinePlayer sender) {
        this.sender = sender;
    }
}

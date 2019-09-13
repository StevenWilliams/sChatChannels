package org.stevenw.mc.chatchannels;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MessageEvent extends Event {
    private Message m;
    private int receivedByCount = 0;
    public MessageEvent (Message message) {
        this.m =message;
    }
    public Message getMessage() {
        return m;
    }
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public int getReceivedByCount() {
        return receivedByCount;
    }
    public void setReceivedByCount(int count) {
        this.receivedByCount = count;
    }
}

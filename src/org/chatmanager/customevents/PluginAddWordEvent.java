package org.chatmanager.customevents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.chatmanager.util.Word;

public class PluginAddWordEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Word word;
    private boolean cancelled;

    public PluginAddWordEvent(Word word) {
        this.word = word;
    }

    public String getWord() {
        return word.toString();
    }

    public boolean wordExist() {
        return word.exists();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}

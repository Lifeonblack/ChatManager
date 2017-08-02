package org.chatmanager.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class PlayerSwearEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private List<String> wordsToReplace;
    private boolean cancelled;

    public PlayerSwearEvent(List<String> wordsToReplace, Player player) {
        this.wordsToReplace = wordsToReplace;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    public List<String> getWordsToReplace() {
        if(!wordsToReplace.isEmpty()) {
            return wordsToReplace;
        }
        return null;
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

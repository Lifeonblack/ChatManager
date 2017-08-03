package org.chatmanager.executors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.chatmanager.ChatManager;
import org.chatmanager.api.ApiManager;
import org.chatmanager.api.ChatManagerApi;
import org.chatmanager.collections.Lists;
import org.chatmanager.commands.AbstractCommand;
import org.chatmanager.commands.AddWordCommand;
import org.chatmanager.commands.ChatManagerReloadCommand;
import org.chatmanager.commands.RemoveWordCommand;
import org.chatmanager.util.Word;

public class ChatManagerExecutor implements CommandExecutor {
    ApiManager apiManager = ChatManager.getApi();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        AbstractCommand reloadCommand = new ChatManagerReloadCommand(sender);
        if(cmd.getName().equalsIgnoreCase("chatmanager")) {

            if(args.length == 0) {
                if(!sender.hasPermission("chatmanager.admin")) {
                    sender.sendMessage(ChatManager.getApi().getLanguage().getString("noPermission"));
                    return true;
                }
                String message = "&8[&bCustomRecipe&8] &bv" + ChatManager.getInstance().getDescription().getVersion();
                String message1 = "&8[&bCustomRecipe&8] &bCreated by : &bLifeonblack";
                String message2 = "&8[&bCustomRecipe&8] &b/" + label + " reload : &7to reload all files";
                sender.sendMessage(new Word(message).colorize());
                sender.sendMessage(new Word(message1).colorize());
                sender.sendMessage(new Word(message2).colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &b/" + label + " addword {WORD} : &7to add word").colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &b/" + label + " removeword {WORD} : &7to remove word").colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " mute <player> : &7to mute a player").colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " unmute <player> : &7to mute a player").colorize());
                sender.sendMessage(new Word("&8[&bChatManager&8] &b/" + label + " toggle <chat or receive> : &7chat to lock your chat and receive so you cannot receive message").colorize());
                return true;
            }

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reload")) {
                    reloadCommand.execute(sender, cmd, label, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("addword")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " addword {WORD} : &7to add word").colorize());
                    return true;
                }
                if(args[0].equalsIgnoreCase("removeword")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " removeword {WORD} : &7to remove word").colorize());
                    return true;
                }
                if(args[0].equalsIgnoreCase("toggle")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " toggle <chat or receive> : &7to toggle whether you can send message or receive a message").colorize());
                    return true;
                }
                if(args[0].equalsIgnoreCase("mute")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " mute <player> : &7to mute a player").colorize());
                    return true;
                }
                if(args[0].equalsIgnoreCase("unmute")) {
                    sender.sendMessage(new Word("&8[&bChatManager&8] &c/" + label + " unmute <player> : &7to mute a player").colorize());
                    return true;
                }
                sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
                return true;
            }

            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("addword")) {
                    AbstractCommand abstractCommand = new AddWordCommand(sender);
                    abstractCommand.execute(sender, cmd, label, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("removeword")) {
                    AbstractCommand abstractCommand = new RemoveWordCommand(sender);
                    abstractCommand.execute(sender, cmd, label, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("toggle")) {
                    if(!(sender instanceof Player)) {
                        sender.sendMessage(apiManager.getLanguage().getString("onlyForPlayer"));
                        return true;
                    }

                    switch (args[1].toLowerCase()) {

                        case "chat":
                            apiManager.lockChat((Player) sender);
                            break;
                        case "receive":
                            apiManager.removeReceiveAbility((Player) sender);
                            break;
                        default:
                            sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
                            break;
                    }
                    return true;
                }
                if(args[0].equalsIgnoreCase("mute")) {
                    Player target = Bukkit.getServer().getPlayer(args[1]);
                    if(!target.hasPlayedBefore()) {
                        sender.sendMessage(apiManager.getLanguage().getString("playerMustExist"));
                        return true;
                    }
                    if(target == null) {
                        OfflinePlayer offlinePlayer = target;
                        apiManager.mute(offlinePlayer);
                        sender.sendMessage(apiManager.getLanguage().getString("playerMuted"));
                        return true;
                    }
                    ChatManagerApi chatManagerApi = (ChatManagerApi)apiManager;
                    chatManagerApi.mute(target);
                    sender.sendMessage(apiManager.getLanguage().getString("playerMuted"));
                    return true;
                }

                if(args[0].equalsIgnoreCase("unmute")) {
                    Player target = Bukkit.getServer().getPlayer(args[1]);
                    if(!target.hasPlayedBefore()) {
                        sender.sendMessage(apiManager.getLanguage().getString("playerMustExist"));
                        return true;
                    }
                    if(target == null) {
                        OfflinePlayer offlinePlayer = target;
                        apiManager.unMute(offlinePlayer);
                        sender.sendMessage(apiManager.getLanguage().getString("playerUnMuted"));
                        return true;
                    }
                    ChatManagerApi chatManagerApi = (ChatManagerApi)apiManager;
                    chatManagerApi.unMute(target);
                    sender.sendMessage(apiManager.getLanguage().getString("playerUnMuted"));
                    return true;
                }
                sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
                return true;
            }

            sender.sendMessage(ChatManager.getApi().getLanguage().getString("unknownCommand"));
            return true;
        }
        return false;
    }
}

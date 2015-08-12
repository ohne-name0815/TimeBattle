package plugin.timebattle.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.timebattle.Main;

public class MessageUtils {

    private static Main MAIN;
    private static String PREFIX = "§7[TB] §r";

    public static void init(Main main) {
        MAIN = main;
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(PREFIX + message);
    }

    public static void broadcast(String message) {
        for(String playerName : MAIN.game.players) {
            Player player = Bukkit.getPlayer(playerName);
            send(player, message);
        }
    }

}

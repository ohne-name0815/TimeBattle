package plugin.timebattle.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.timebattle.Main;

import java.util.ArrayList;
import java.util.List;

public class timebattle implements CommandExecutor {

    Main main;

    public timebattle(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(args[0].equalsIgnoreCase("addworld")) {
            if(args.length > 1) {
                // TODO Add world to config
                String worldName = ((Player) commandSender).getWorld().getName();

                if(!main.getConfig().contains("worlds." + worldName)) {
                    String timeName = "";
                    for(int i = 1; i < args.length; i++) {
                        timeName += args[i] + " ";
                    }
                    timeName = timeName.trim();

                    main.getConfig().set("worlds." + worldName + ".enabled", false);
                    main.getConfig().set("worlds." + worldName + ".worldName", worldName);
                    main.getConfig().set("worlds." + worldName + ".timeName", timeName.trim());
                    main.getConfig().set("worlds." + worldName + ".spawns", new ArrayList<String>());

                    main.saveConfig();

                    commandSender.sendMessage("ERFOLGREICH HINZUGEFÜGT!");
                }
            }
        }

        if(args[0].equalsIgnoreCase("addspawn")) {
            String worldName = ((Player) commandSender).getWorld().getName();

            if(main.getConfig().contains("worlds." + worldName)) {
                Player player = ((Player) commandSender);
                List<String> spawns = main.getConfig().getStringList("worlds." + worldName + ".spawns");
                spawns.add(player.getLocation().getBlockX() + " -- " +
                        player.getLocation().getBlockY() + " -- " +
                        player.getLocation().getBlockZ() + " -- " +
                        player.getLocation().getYaw() + " -- " +
                        player.getLocation().getPitch());
                main.getConfig().set("worlds." + worldName + ".spawns", spawns);
                main.saveConfig();
            }
        }

        return true;
    }

}

package plugin.timebattle.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.timebattle.Main;
import plugin.timebattle.utils.MessageUtils;

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

                    MessageUtils.send(commandSender, "§7Die Zeitepoche §b" + timeName + " §7wurde erfolgreich erstellt!");
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

                MessageUtils.send(commandSender, "§7Spawnpunkt hinzugefügt. Anzahl der Spawnpunkte: §b" + spawns.size());
            }
        }

        if(args[0].equalsIgnoreCase("enabletime")) {
            String worldName = ((Player) commandSender).getWorld().getName();
            main.getConfig().set("worlds." + worldName + ".enabled", true);
            main.saveConfig();
        }

        if(args[0].equalsIgnoreCase("disabletime")) {
            String worldName = ((Player) commandSender).getWorld().getName();
            main.getConfig().set("worlds." + worldName + ".enabled", false);
            main.saveConfig();
        }

        if(args[0].equalsIgnoreCase("enable")) {
            main.getConfig().set("ENABLED", true);
            main.saveConfig();
        }

        if(args[0].equalsIgnoreCase("disable")) {
            main.getConfig().set("ENABLED", false);
            main.saveConfig();
        }

        return true;
    }

}

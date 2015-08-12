package plugin.timebattle.threads;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import plugin.timebattle.Main;

import java.util.HashMap;

public class FreezeThread implements Runnable {

    private Main main;

    private HashMap<String, Location> playerFreezeLocations = new HashMap<>();

    public FreezeThread(Main main) {
        this.main = main;
    }

    public void addPlayer(Player player, Location location) {
        playerFreezeLocations.put(player.getName(), location);
    }

    public void removePlayer(Player player) {
        playerFreezeLocations.remove(player.getName());
    }

    @Override
    public void run() {
        while (true) {
            try {
                for(String playerName : playerFreezeLocations.keySet()) {
                    Player player = Bukkit.getPlayer(playerName);
                    Location location = playerFreezeLocations.get(playerName);

                    if(player.getLocation().getBlockX() != location.getBlockX() ||
                            player.getLocation().getBlockZ() != location.getBlockZ()) {
                        location.setYaw(player.getLocation().getYaw());
                        location.setPitch(player.getLocation().getPitch());
                        player.teleport(location);
                    }
                }
            } catch (Exception e) {

            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

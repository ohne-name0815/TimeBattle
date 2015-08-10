package plugin.timebattle.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import plugin.timebattle.Main;
import plugin.timebattle.utils.TimeWorld;
import plugin.timebattle.utils.TimeWorldUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Main main;

    private TimeWorld spawnWorld;
    private TimeWorld currentWorld;
    private List<TimeWorld> timeWorlds;
    private List<Location> spawns;

    public List<String> players = new ArrayList<>();

    public Game(Main main) {
        this.main = main;

        this.timeWorlds = TimeWorldUtils.getTimeWorlds(false);

        Random random = new Random();
        this.spawnWorld = timeWorlds.get(random.nextInt(timeWorlds.size()));
        currentWorld = spawnWorld;

        timeWorlds.remove(currentWorld);

        this.spawns = new ArrayList<>(spawnWorld.spawns);
    }

    public void join(Player player) {
        this.players.add(player.getName());

        Random random = new Random();
        int index = random.nextInt(spawns.size());
        Location spawn = spawns.get(index);
        this.spawns.remove(index);

        player.teleport(spawn);
        main.freezeThread.addPlayer(player, spawn);
    }

    public void leave(Player player) {
        this.players.remove(player.getName());
        player.kickPlayer("ENDE");
    }

    public void changeWorld() {

    }

}

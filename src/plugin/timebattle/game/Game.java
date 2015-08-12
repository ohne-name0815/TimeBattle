package plugin.timebattle.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import plugin.timebattle.Main;
import plugin.timebattle.threads.GameStartTimer;
import plugin.timebattle.utils.MessageUtils;
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

    private int maxPlayers = -1;



    public boolean started = false;


    // TIMER
    private Thread gameStartTimer;



    // GAME VARIABLES
    private int WORLD_CHANGE_DELAY_SECONDS = 5 * 60;



    public Game(Main main) {
        this.main = main;

        this.timeWorlds = TimeWorldUtils.getTimeWorlds(false);

        Random random = new Random();
        this.spawnWorld = timeWorlds.get(random.nextInt(timeWorlds.size()));
        currentWorld = spawnWorld;

        timeWorlds.remove(currentWorld);

        this.spawns = new ArrayList<>(spawnWorld.spawns);

        main.setServerMaxPlayerCount(spawns.size());
        maxPlayers = spawns.size();
    }

    public void join(Player player) {
        if(this.players.size() >= maxPlayers) {
            player.kickPlayer("");
            return;
        }

        this.players.add(player.getName());

        Random random = new Random();
        int index = random.nextInt(spawns.size());
        Location spawn = spawns.get(index);
        this.spawns.remove(index);

        player.teleport(spawn.clone().add(0.5, 0, 0.5));
        main.freezeThread.addPlayer(player, spawn.clone().add(0.5, 0, 0.5));

        updateStartTimer();
    }

    public void leave(Player player) {
        this.players.remove(player.getName());
        player.kickPlayer("ENDE");

        updateStartTimer();
    }

    public void updateStartTimer() {
        if(this.players.size() >= (this.maxPlayers / 2.0d)) {
            if(this.gameStartTimer == null) {
                MessageUtils.broadcast("§7Das Spiel startet in 30 Sekunden!");
                this.gameStartTimer = new Thread(new GameStartTimer(main, 30, new Runnable() {
                    @Override
                    public void run() {
                        startGame();
                    }
                }));
                this.gameStartTimer.start();
            }
        } else {
            if(this.gameStartTimer != null) {
                this.gameStartTimer.interrupt();
                this.gameStartTimer.stop();

                this.gameStartTimer = null;

                MessageUtils.broadcast("§7Der Countdown wurde abgebrochen, da sich zu wenig Spieler im Spiel befinden!");
            }
        }
    }

    public void startGame() {
        MessageUtils.broadcast("§7Das Spiel beginnt!");

        this.started = true;

        for(String playerName : this.players) {
            Player player = Bukkit.getPlayer(playerName);
            main.freezeThread.removePlayer(player);
        }
    }

    public void changeWorld() {
        // TODO PlayerEffects

        if(timeWorlds.size() == 0) {
            // TODO Deathmatch
        } else {
            Random random = new Random();
            TimeWorld newWorld = timeWorlds.get(random.nextInt(timeWorlds.size()));
            timeWorlds.remove(newWorld);
            currentWorld = newWorld;

            List<Location> spawns = new ArrayList<>(currentWorld.spawns);

            for(String playerName : this.players) {
                Player player = Bukkit.getPlayer(playerName);

                Location spawn = spawns.get(random.nextInt(spawns.size()));
                spawns.remove(spawn);

                player.teleport(spawn);
            }
        }
    }

}

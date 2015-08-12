package plugin.timebattle;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.timebattle.commands.timebattle;
import plugin.timebattle.game.Game;
import plugin.timebattle.listener.PlayerBuildListener;
import plugin.timebattle.listener.ServerListener;
import plugin.timebattle.listener.world.BlockBreakListener;
import plugin.timebattle.threads.FreezeThread;
import plugin.timebattle.utils.MessageUtils;
import plugin.timebattle.utils.TimeWorld;
import plugin.timebattle.utils.TimeWorldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public List<TimeWorld> timeWorlds;

    public FreezeThread freezeThread;
    public Game game;

    @Override
    public void onEnable() {
        super.onEnable();

        //getDataFolder().mkdir();

        MessageUtils.init(this);

        initConfig();
        TimeWorldUtils.init(this);

        timeWorlds = new ArrayList<>();
        initWorlds();

        registerListener();

        getCommand("timebattle").setExecutor(new timebattle(this));

        if(getConfig().getBoolean("ENABLED")) {
            freezeThread = new FreezeThread(this);
            new Thread(freezeThread).start();

            getLogger().info("Game is creating...");
            game = new Game(this);
            getLogger().info("Game created!");
        }

        getLogger().info("TimeBattle successfully enabled!");
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBuildListener(), this);
        getServer().getPluginManager().registerEvents(new ServerListener(this), this);
    }

    private void initConfig() {
        getConfig().addDefault("ENABLED", false);
        getConfig().options().copyDefaults(true);
    }

    public void setServerMaxPlayerCount(int maxPlayers) {
        getLogger().info("Updating player count to " + maxPlayers);
        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Object playerlist = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".CraftServer")
                    .getDeclaredMethod("getHandle", null).invoke(Bukkit.getServer(), null);
            Field maxplayers = playerlist.getClass().getSuperclass()
                    .getDeclaredField("maxPlayers");
            maxplayers.setAccessible(true);
            maxplayers.set(playerlist, maxPlayers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info("TimeBattle successfully disabled!");
    }

    private void initWorlds() {
        if(getConfig().contains("worlds")) {
            for(String worldKey : getConfig().getConfigurationSection("worlds").getKeys(false)) {
                Boolean enabled = getConfig().getBoolean("worlds." + worldKey + ".enabled");
                String worldName = getConfig().getString("worlds." + worldKey + ".worldName");
                String timeName = getConfig().getString("worlds." + worldKey + ".timeName");

                List<String> spawns = getConfig().getStringList("worlds." + worldKey + ".spawns");

                TimeWorld timeWorld = new TimeWorld(worldName, timeName, enabled, spawns);
                this.timeWorlds.add(timeWorld);
            }
        }
    }

}

package plugin.timebattle;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.timebattle.commands.timebattle;
import plugin.timebattle.game.Game;
import plugin.timebattle.listener.PlayerBuildListener;
import plugin.timebattle.listener.ServerListener;
import plugin.timebattle.threads.FreezeThread;
import plugin.timebattle.utils.TimeWorld;
import plugin.timebattle.utils.TimeWorldUtils;

import java.io.File;
import java.io.IOException;
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

        initConfig();
        TimeWorldUtils.init(this);

        timeWorlds = new ArrayList<>();
        initWorlds();

        getServer().getPluginManager().registerEvents(new PlayerBuildListener(), this);
        getServer().getPluginManager().registerEvents(new ServerListener(this), this);

        freezeThread = new FreezeThread(this);
        new Thread(freezeThread).start();

        getCommand("timebattle").setExecutor(new timebattle(this));

        getLogger().info("Game is creating...");
        game = new Game(this);
        getLogger().info("Game created!");

        getLogger().info("TimeBattle successfully enabled!");
    }

    private void initConfig() {
        getConfig().options().copyDefaults(true);
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

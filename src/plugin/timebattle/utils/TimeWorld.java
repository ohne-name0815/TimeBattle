package plugin.timebattle.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class TimeWorld {

    private boolean enabled;
    private String worldName;
    private String timeName;

    public List<Location> spawns = new ArrayList<>();

    public TimeWorld(String worldName, String timeName, boolean enabled, List<String> spawns) {
        this.worldName = worldName;
        this.timeName = timeName;
        this.enabled = enabled;

        for(String spawnString : spawns) {
            int x = Integer.parseInt(spawnString.split(" -- ")[0]);
            int y = Integer.parseInt(spawnString.split(" -- ")[1]);
            int z = Integer.parseInt(spawnString.split(" -- ")[2]);
            float yaw = Float.parseFloat(spawnString.split(" -- ")[3]);
            float pitch = Float.parseFloat(spawnString.split(" -- ")[4]);

            Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            this.spawns.add(location);
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public String getTimeName() {
        return timeName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

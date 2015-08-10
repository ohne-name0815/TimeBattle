package plugin.timebattle.utils;

import plugin.timebattle.Main;

import java.util.ArrayList;
import java.util.List;

public class TimeWorldUtils {

    private static Main MAIN;

    public static void init(Main main) {
        MAIN = main;
    }

    public static List<TimeWorld> getTimeWorlds(boolean onlyEnabled) {
        if(!onlyEnabled) {
            return MAIN.timeWorlds;
        } else {
            List<TimeWorld> back = new ArrayList<>();
            for(TimeWorld timeWorld : MAIN.timeWorlds) {
                if(timeWorld.isEnabled()) {
                    back.add(timeWorld);
                }
            }

            return back;
        }
    }

    public static boolean containsWorldName(List<TimeWorld> timeWorlds, String worldName) {
        for(TimeWorld timeWorld : timeWorlds) {
            if(timeWorld.getWorldName().equals(worldName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsTimeName(List<TimeWorld> timeWorlds, String timeName) {
        for(TimeWorld timeWorld : timeWorlds) {
            if(timeWorld.getTimeName().equals(timeName)) {
                return true;
            }
        }
        return false;
    }

}

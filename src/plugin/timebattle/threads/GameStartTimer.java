package plugin.timebattle.threads;

import org.bukkit.Bukkit;
import plugin.timebattle.Main;
import plugin.timebattle.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class GameStartTimer implements Runnable {

    Main main;
    int counter;
    Runnable runnable;

    public List<Integer> remindingSeconds = new ArrayList<>();

    public GameStartTimer(Main main, int seconds, Runnable runnable) {
        this.main = main;
        this.counter = seconds;

        remindingSeconds.add(10);
        remindingSeconds.add(5);
        remindingSeconds.add(4);
        remindingSeconds.add(3);
        remindingSeconds.add(2);
        remindingSeconds.add(1);

        this.runnable = runnable;
    }

    @Override
    public void run() {
        for(counter = counter; counter >= 0; counter--) {
            if(remindingSeconds.contains(counter)) {
                // TODO REMIND
                MessageUtils.broadcast("§7Das Spiel beginnt in §b" + counter + " §7Sekunden!");
            }

            if(counter == 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(main, runnable);

                Thread.currentThread().interrupt();
                Thread.currentThread().stop();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

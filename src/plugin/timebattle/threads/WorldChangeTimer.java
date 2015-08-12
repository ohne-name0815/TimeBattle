package plugin.timebattle.threads;

import org.bukkit.Bukkit;
import plugin.timebattle.Main;
import plugin.timebattle.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class WorldChangeTimer implements Runnable {

    private Main main;
    private int counter;
    private Runnable runnable;

    private List<Integer> remindingSeconds = new ArrayList<>();

    public WorldChangeTimer(Main main, int counter, Runnable runnable) {
        this.main = main;
        this.counter = counter;
        this.runnable = runnable;

        remindingSeconds.add(120);
        remindingSeconds.add(60);
        remindingSeconds.add(30);
        remindingSeconds.add(10);
        remindingSeconds.add(5);
        remindingSeconds.add(4);
        remindingSeconds.add(3);
        remindingSeconds.add(2);
        remindingSeconds.add(1);
    }


    @Override
    public void run() {
        for(counter = counter; counter >= 0; counter--) {
            if(remindingSeconds.contains(counter)) {
                if(counter / 60 > 0 && counter % 60 == 0) {
                    MessageUtils.broadcast(counter / 60 > 0 && counter % 60 == 0 ?
                            "§7Die Zeitepoche wird in §b" + (counter / 60) + " §7Minuten gewechselt!" :
                            "§7Die Zeitepoche wird in §b" + counter + " §7Sekunden gewechselt!");
                }
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

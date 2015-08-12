package plugin.timebattle.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import plugin.timebattle.Main;

public class ServerListener implements Listener {

    Main main;

    public ServerListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO Join game
        if(main.game != null && !main.game.started) {
            main.game.join(event.getPlayer());
        } else {
            event.getPlayer().kickPlayer("§4Das Spiel hat bereits begonnen!");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // TODO Leave game
    }

}

package tc.scoreboard.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tc.scoreboard.manager.ScoreboardUpdater;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		ScoreboardUpdater.updateScoreboard();
	}

}

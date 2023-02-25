package tc.scoreboard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tc.scoreboard.api.ScoreboardAPI;
import tc.scoreboard.enums.ToggleStatus;
import tc.scoreboard.manager.ScoreboardUpdater;

public class ToggleScoreboardCommand implements CommandExecutor {

	ScoreboardAPI sa = new ScoreboardAPI();
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player p = (Player) sender;
		
		sa.setOppositeToggleStatus(p);
		
		if(sa.getToggleStatus(p) != ToggleStatus.OFF) {
			ScoreboardUpdater.createScoreboard(p);
		} else {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		
		return false;
	}
}

package tc.scoreboard.manager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import tc.colors.SpigotColors;
import tc.replacements.Replacement;
import tc.scoreboard.Main;
import tc.scoreboard.api.ScoreboardAPI;
import tc.scoreboard.enums.ToggleStatus;

public class ScoreboardUpdater {
	
	static ScoreboardAPI sa = new ScoreboardAPI();
	
	public static void createScoreboard(Player p) {
		ScoreboardManager manager = (ScoreboardManager) Bukkit.getScoreboardManager();
		Scoreboard sc = manager.getNewScoreboard();
		Objective obj = sc.registerNewObjective("Stats", "dummy");
		obj.setDisplayName(SpigotColors.parse(Main.scoreboardTitle));
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		List<String> replacedList = Replacement.replaceStringList(Main.scoreboardLines, Replacement.getAllReplacements(p));
		
		String nextEmptyLineFix = "";
		
		for(int i = 0; i < replacedList.size(); i++) {
			if(replacedList.get(i) == null || replacedList.get(i) == "" || replacedList.get(i) == " ") { 
				nextEmptyLineFix += " ";
				replacedList.set(i, nextEmptyLineFix);
			}
			
			setScore(replacedList.size() - i, obj, replacedList.get(i));
		}
		
		p.setScoreboard(sc);
	}
	
	public static void setScore(int x, Objective obj, String text) {
		Score score = obj.getScore(SpigotColors.parse(text));
		score.setScore(x);
	}
	
	public static void updateScoreboard() {
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(sa.getToggleStatus(players) != ToggleStatus.OFF) {
				createScoreboard(players);
			}
		}
	}
}

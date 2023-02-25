package tc.scoreboard.api;

import org.bukkit.entity.Player;

import tc.scoreboard.data.ToggleScoreboardData;
import tc.scoreboard.enums.ToggleStatus;

public class ScoreboardAPI {
	
	ToggleScoreboardData tsd = ToggleScoreboardData.getInstance();
	
	public boolean isPlayerInData(Player p) {
		return tsd.getData().contains(p.getUniqueId().toString());
	}
	
	public String getRawToggleStatus(Player p) {
		return tsd.getData().getString(p.getUniqueId() + ".status");
	}
	
	public boolean isToggleStatus(String toggleStatusToCheck) {
		try { ToggleStatus.valueOf(toggleStatusToCheck); } catch(Exception e) { return false; }
		return true;
	}
	
	public ToggleStatus getToggleStatus(Player p) {
		String raw = getRawToggleStatus(p);
		
		if(raw == null || !isToggleStatus(raw)) { return ToggleStatus.ON; }
		
		return ToggleStatus.valueOf(tsd.getData().getString(p.getUniqueId() + ".status"));
	}
	
	public void setOppositeToggleStatus(Player p) {
		setToggleStatus(p, (getToggleStatus(p) == ToggleStatus.ON ? ToggleStatus.OFF : ToggleStatus.ON) );
	}
	
	public void setToggleStatus(Player p, ToggleStatus ts) {
		tsd.getData().set(p.getUniqueId() + ".status", ts.toString());
		tsd.saveData();
	}
}

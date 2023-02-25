package tc.scoreboard;

import java.util.List; 

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import tc.consolemessages.ConsoleMessage;
import tc.enums.PluginLicenseTypes;
import tc.scoreboard.commands.ToggleScoreboardCommand;
import tc.scoreboard.data.ToggleScoreboardData;
import tc.scoreboard.events.PlayerJoin;
import tc.scoreboard.manager.ScoreboardUpdater;
import tc.updatechecker.UpdateChecker;
import tc.validators.JavaValidator;

public class Main extends JavaPlugin implements CommandExecutor {
	
	private String pluginName = "tc-scoreboard-1-8-8";
	private PluginLicenseTypes pluginLicenseType = PluginLicenseTypes.FREE;
	
	private static Main instance;
	
	public static int refreshTime;
	public static String scoreboardTitle;
	public static List<String> scoreboardLines;
	
	public String prefix = "§8[§cTC-Scoreboard§8] §7";
	public String discordInvite = "https://discord.gg/EcAaMdn8yP";
	public String dcInfoErr = "Jezeli nie wiesz co oznacza ten blad to mozesz uzyskac pomoc na discordzie: " + discordInvite;
	
	public ToggleScoreboardData tsd = ToggleScoreboardData.getInstance();
	
	public void onEnable() {
		instance = this;
		
		tsd.setup(this);
		
		UpdateChecker.sendCustomMessage(pluginName, pluginLicenseType);
		UpdateChecker.checkForUpdate(pluginName, pluginLicenseType, getDescription().getVersion());
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		saveConfig();
		
		ConsoleMessage.sendConsoleMsg(prefix,
			new String[] {
				"&7Plugin zostal zaladowany.",
				"&7Jezeli masz jakis blad w pluginie wejdz na discorda &a" + discordInvite + "&7 i go zglos.",
				"&7Autor: &atechnic_cup"
			}
		);
		
		if(!JavaValidator.isInt(getConfig().getString("scoreboard.refreshTime"))
				&& ConsoleMessage.sendConsoleMsg(prefix + "&cBledny zapis odswierzania scoreboard, scoreboard.refreshTime " + dcInfoErr, instance))
			return;
		
		refreshTime = getConfig().getInt("scoreboard.refreshTime");
		
		if(JavaValidator.isNull(getConfig().getStringList("scoreboard.title"))
				&& ConsoleMessage.sendConsoleMsg(prefix + "&cBledny zapis tytulu scoreboard, scoreboard.title. " + dcInfoErr, instance)) {
			return;
		}
		
		scoreboardTitle = getConfig().getString("scoreboard.title");
		
		if(!JavaValidator.isStringList(getConfig().getStringList("scoreboard.lines"))
				&& ConsoleMessage.sendConsoleMsg(prefix + "&cBledny zapis listy wiadomosci pomocy. " + dcInfoErr, instance))
			return;
		
		scoreboardLines = getConfig().getStringList("scoreboard.lines");
		
		getCommand("scoreboard").setExecutor(new ToggleScoreboardCommand());
		
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		
		ScoreboardUpdater.updateScoreboard();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				ScoreboardUpdater.updateScoreboard();
			}
		}, 0L, refreshTime * 20);
	}
	
	public static Main getInstance() {
		return instance;
	}

}

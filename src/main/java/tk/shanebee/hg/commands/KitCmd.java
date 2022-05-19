package tk.shanebee.hg.commands;

import org.bukkit.Bukkit;
import tk.shanebee.hg.Status;
import tk.shanebee.hg.game.Game;
import tk.shanebee.hg.util.Util;
public class KitCmd extends BaseCmd {

	public KitCmd() {
		forcePlayer = true;
		cmdName = "kit";
		forceInGame = true;
		argLength = 2;
		usage = "<kit>";
	}

	@Override
	public boolean run() {
		Game game = playerManager.getPlayerData(player).getGame();
		if (!game.getKitManager().hasKits()) {
		    Util.scm(player, lang.kit_disabled);
		    return false;
        }
		if (game.getGameArenaData().getStatus() == Status.BEGINNING) {
			game.getKitManager().setKit(player, args[1]);
			//Bukkit.getScheduler().runTaskLater(plugin, () -> game.getKitManager().setKit(player, args[1]), 10L);
		} else {
			Util.scm(player, lang.cmd_kit_no_change);
		}
		return true;
	}

}

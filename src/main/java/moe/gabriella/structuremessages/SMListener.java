package moe.gabriella.structuremessages;

import moe.gabriella.gbmpa.fixedupdater.PAFixedUpdate;
import moe.gabriella.gbmpa.utils.PAConsts;
import moe.gabriella.structuremessages.data.PlayerStructureData;
import moe.gabriella.structuremessages.data.SMStructure;
import moe.gabriella.structuremessages.runnables.EnteredRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SMListener implements Listener {

    @EventHandler
    public void onFixedUpdate(PAFixedUpdate event) {
        if (!event.getUpdateType().equals("StructureCheck"))
            return;

        SMPlugin p = SMPlugin.getInstance();

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            SMStructure type = SMPlugin.getInstance().checkStructure(player);
            PlayerStructureData data = p.getPlayerStructure(player);
            if (type == null) {
                if (data.getCurrentStructure() != null)
                    data.setLastStructure(data.getCurrentStructure());
                data.setCurrentStructure(null);
            } else {
                if (p.getPlayerStructure(player).getCurrentStructure() == null) { // player has just entered, time for the fun shit!
                    data.setCurrentStructure(type);
                    if (data.getLastStructure() != type)
                        new EnteredRunnable(player, type).runTaskAsynchronously(PAConsts.plugin);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SMPlugin.getInstance().getCurrentStructures().put(event.getPlayer(), new PlayerStructureData());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        SMPlugin.getInstance().getCurrentStructures().remove(event.getPlayer());
    }

}

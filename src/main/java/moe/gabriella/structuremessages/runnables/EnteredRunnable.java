package moe.gabriella.structuremessages.runnables;

import moe.gabriella.gbmpa.utils.PALogger;
import moe.gabriella.structuremessages.data.SMStructure;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class EnteredRunnable extends BukkitRunnable {

    Player player;
    SMStructure type;

    ChatColor primaryColour;
    ChatColor secondaryColour;
    String areaName;

    public EnteredRunnable(Player player, SMStructure type) {
        this.player = player;
        this.type = type;

        areaName = type.getName().toUpperCase();
        this.primaryColour = type.getPrimary();
        this.secondaryColour = type.getSecondary();
    }

    @Override
    public void run() {
        try {
            player.sendTitle("", "Now entering...", 10, 50, 0);
            TimeUnit.SECONDS.sleep(2);

            player.sendTitle(secondaryColour + "" + ChatColor.MAGIC + "1" + ChatColor.RESET + " " + primaryColour + "" + ChatColor.BOLD + areaName + " " + secondaryColour + "" + ChatColor.MAGIC + "1", "Now entering...", 0, 60, 0);
            if (type.playSound()) player.playSound(player.getLocation(), type.getCaveSound(), (type.getCaveSound() == Sound.AMBIENT_CAVE ? 1f : 0.3f), 1f);
            TimeUnit.SECONDS.sleep(2);

            int curr = 0;
            while (curr != areaName.length()) {
                StringBuilder out = new StringBuilder(primaryColour + "" + ChatColor.BOLD);

                for (int i=0; i<areaName.length(); i++) {
                    char c = areaName.charAt(i);
                    if (i == curr)
                        out.append(secondaryColour).append(ChatColor.BOLD);
                    if (i == curr + 1)
                        out.append(ChatColor.WHITE + "" + ChatColor.BOLD);

                    out.append(c).append(primaryColour).append(ChatColor.BOLD);
                }

                curr++;

                if (curr == areaName.length()) {
                    out = new StringBuilder(primaryColour + "" + ChatColor.BOLD + areaName);
                }

                player.sendTitle(secondaryColour + "" + ChatColor.MAGIC + "1" + ChatColor.RESET + " " + out.toString() + " " + secondaryColour + "" + ChatColor.MAGIC + "1", "Now entering...", 0, 60, (curr == areaName.length() ? 20 : 0));
                TimeUnit.MILLISECONDS.sleep(25);
            }
            TimeUnit.SECONDS.sleep(3);
            if (type.isThunder()) player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.3f, 0.3f);

        } catch (Exception e) {
            PALogger.error("Well this is awkward! Error executing entered runnable: ");
            e.printStackTrace();
        }
    }

}

package moe.gabriella.structuremessages;

import lombok.Getter;
import moe.gabriella.gbmpa.PAPlugin;
import moe.gabriella.gbmpa.fixedupdater.FixedUpdater;
import moe.gabriella.gbmpa.utils.PALogger;
import moe.gabriella.structuremessages.data.PlayerStructureData;
import moe.gabriella.structuremessages.data.SMStructure;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class SMPlugin extends PAPlugin {

    @Getter private static SMPlugin instance;

    private ArrayList<SMStructure> typesToCheck;
    @Getter private HashMap<Player, PlayerStructureData> currentStructures;

    public SMPlugin() {
        super("StructureMessages", "1.1");
    }

    @Override
    public void onPluginEnable() {
        instance = this;

        typesToCheck = new ArrayList<>();
        for (SMStructure s : SMStructure.values())
            addType(s);

        currentStructures = new HashMap<>();
        getServer().getPluginManager().registerEvents(new SMListener(), this);
        FixedUpdater.createFixedUpdate("StructureCheck", 10);
    }

    private void addType(SMStructure type) {
        typesToCheck.add(type);
        PALogger.debug("Now checking for type " + type.getName());
    }


    public SMStructure checkStructure(Player player) {
        World world = ((CraftWorld) player.getWorld()).getHandle();

        for (SMStructure type : typesToCheck) {
            StructureGenerator nmsType = type.getNmsType();
            if (nmsType == null) {
                PALogger.warn("Unsupported type: " + type.getName());
                continue;
            }

            Location loc = player.getWorld().locateNearestStructure(player.getLocation(), type.getType(), 5, false);

            if (loc == null)
                continue;

            Chunk chunk = world.getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ());
            if (chunk.a(nmsType) != null) { // Checking if this chunk is the starting point of the structure
                for (StructurePiece piece : chunk.a(nmsType).d()) { // Iterating through every piece of the structure
                    if (piece.g().b(new BaseBlockPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()))) { // Getting the piece's bounding box and then checking if the player is inside
                        return type; // If all is true, player is standing in the structure
                    }
                }
            }
        }

        return null;
    }

    public PlayerStructureData getPlayerStructure(Player player) {
        if (!currentStructures.containsKey(player))
            currentStructures.put(player, new PlayerStructureData());

        return currentStructures.get(player);
    }
}

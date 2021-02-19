package moe.gabriella.structuremessages.data;

import lombok.Getter;
import net.minecraft.server.v1_16_R3.StructureGenerator;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.StructureType;

public enum SMStructure {

    STRONGHOLD("Stronghold", StructureType.STRONGHOLD, StructureGenerator.STRONGHOLD, ChatColor.RED, ChatColor.DARK_RED, Sound.AMBIENT_CAVE, true),
    END_CITY("End City", StructureType.END_CITY, StructureGenerator.ENDCITY, ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, Sound.BLOCK_PORTAL_TRAVEL, false),
    BASTION("Bastion Remnant", StructureType.BASTION_REMNANT, StructureGenerator.BASTION_REMNANT, ChatColor.GOLD, ChatColor.YELLOW, Sound.AMBIENT_CAVE, false),
    FORTRESS("Nether Fortress", StructureType.NETHER_FORTRESS, StructureGenerator.FORTRESS, ChatColor.RED, ChatColor.DARK_RED, Sound.AMBIENT_CAVE, false),
    JUNGLE("Jungle Pyramid", StructureType.JUNGLE_PYRAMID, StructureGenerator.JUNGLE_PYRAMID, ChatColor.DARK_GREEN, ChatColor.GREEN, Sound.AMBIENT_CAVE, true),
    MINESHAFT("Mineshaft", StructureType.MINESHAFT, StructureGenerator.MINESHAFT, ChatColor.RED, ChatColor.DARK_RED, Sound.AMBIENT_CAVE, true),
    MANSION("Woodland Mansion", StructureType.WOODLAND_MANSION, StructureGenerator.MANSION, ChatColor.DARK_GREEN, ChatColor.GREEN, Sound.AMBIENT_CAVE, true),
    TEMPLE("Desert Temple", StructureType.DESERT_PYRAMID, StructureGenerator.DESERT_PYRAMID, ChatColor.GOLD, ChatColor.YELLOW, Sound.AMBIENT_CAVE, true),
    OUTPOST("Pillager Outpost", StructureType.PILLAGER_OUTPOST, StructureGenerator.PILLAGER_OUTPOST, ChatColor.RED, ChatColor.DARK_RED, Sound.EVENT_RAID_HORN, true);

    @Getter private String name;
    @Getter private StructureType type;
    @Getter private StructureGenerator nmsType;
    @Getter private ChatColor primary;
    @Getter private ChatColor secondary;
    @Getter private Sound caveSound;
    @Getter private boolean thunder;

    private SMStructure(String name, StructureType type, StructureGenerator nmsType, ChatColor primary, ChatColor secondary, Sound caveSound, boolean thunder) {
        this.name = name;
        this.type = type;
        this.nmsType = nmsType;
        this.primary = primary;
        this.secondary = secondary;
        this.caveSound = caveSound;
        this.thunder = thunder;
    }

    public boolean playSound() { return caveSound != null; }

}

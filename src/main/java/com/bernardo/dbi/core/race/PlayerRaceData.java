package com.bernardo.dbi.core.race;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlayerRaceData {
    private static final Map<UUID, Race> RACES = new HashMap<>();
    private static final Map<UUID, FightingClass> CLASSES = new HashMap<>();
    // Cores: [0]=Pele, [1]=Armadura, [2]=Olhos
    private static final Map<UUID, int[]> COLORS = new HashMap<>();

    public static void setAll(UUID uuid, Race race, FightingClass fClass, int[] colors) {
        RACES.put(uuid, race);
        CLASSES.put(uuid, fClass);
        COLORS.put(uuid, colors);
    }

    public static Race getRace(UUID uuid) { return RACES.getOrDefault(uuid, Race.HUMAN); }
    public static FightingClass getFightingClass(UUID uuid) { return CLASSES.getOrDefault(uuid, FightingClass.WARRIOR); }
    public static int[] getColors(UUID uuid) { 
        return COLORS.getOrDefault(uuid, new int[]{0xFFFFFF, 0xCCCCCC, 0xFF0000}); 
    }
}

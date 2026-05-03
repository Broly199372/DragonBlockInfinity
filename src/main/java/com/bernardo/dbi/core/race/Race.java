package com.bernardo.dbi.core.race;

import com.bernardo.dbi.DragonBlockInfinity;
import net.minecraft.resources.ResourceLocation;

public enum Race {
    HUMAN(null),
    ARCONSIAN(new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/cc/male/ac1b.png")),
    SAIYAN(null),
    NAMEKIAN(null),
    ANDROID(null);

    private final ResourceLocation texture;

    Race(ResourceLocation texture) {
        this.texture = texture;
    }

    public ResourceLocation getTexture() { return texture; }
    public boolean hasTexture() { return texture != null; }

    public String getDisplayName() {
        return switch (this) {
            case HUMAN    -> "Human";
            case ARCONSIAN -> "Arconsian";
            case SAIYAN   -> "Saiyan";
            case NAMEKIAN -> "Namekian";
            case ANDROID  -> "Android";
        };
    }
}

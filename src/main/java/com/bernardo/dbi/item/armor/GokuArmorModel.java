package com.bernardo.dbi.item.armor;

import com.bernardo.dbi.DragonBlockInfinity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/** GeoModel da armadura do Goku — usa geo separado por peça. */
public class GokuArmorModel extends GeoModel<GokuArmorItem> {

    @Override
    public ResourceLocation getModelResource(GokuArmorItem item) {
        return switch (item.getType()) {
            case CHESTPLATE -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "geo/armor/goku_chestplate.geo.json");
            case LEGGINGS   -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "geo/armor/goku_leggings.geo.json");
            case BOOTS      -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "geo/armor/goku_boots.geo.json");
            default         -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "geo/armor/goku_chestplate.geo.json");
        };
    }

    @Override
    public ResourceLocation getTextureResource(GokuArmorItem item) {
        return switch (item.getType()) {
            case CHESTPLATE -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/models/armor/dbi_goku_armor_model.png");
            case LEGGINGS   -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/models/armor/dbi_goku_leg_model.png");
            case BOOTS      -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/models/armor/dbi_goku_bot_model.png");
            default         -> new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/models/armor/dbi_goku_armor_model.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(GokuArmorItem item) {
        return null;
    }
}

package com.bernardo.dbi.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

/** Armadura do Goku — textura via getName() "goku" → goku_layer_1/2.png */
public class GokuArmorItem extends ArmorItem {

    public GokuArmorItem(Type type) {
        super(GokuArmorMaterial.INSTANCE, type, new Properties());
    }

    public static int countEquipped(LivingEntity entity) {
        int count = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            if (entity.getItemBySlot(slot).getItem() instanceof GokuArmorItem) count++;
        }
        return count;
    }
}

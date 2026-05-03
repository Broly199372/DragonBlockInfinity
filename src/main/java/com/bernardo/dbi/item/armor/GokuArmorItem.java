package com.bernardo.dbi.item.armor;

import com.bernardo.dbi.DragonBlockInfinity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

/** Armadura do Goku com modelo GeckoLib no corpo do player. */
public class GokuArmorItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Object renderProvider = GeoItem.makeRenderer(this);

    public GokuArmorItem(Type type) {
        super(GokuArmorMaterial.INSTANCE, type, new Properties());
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GokuArmorRenderer renderer;

            @Override
            public GokuArmorRenderer getHumanoidArmorRenderer(
                    LivingEntity entity, ItemStack stack,
                    EquipmentSlot slot, net.minecraft.client.model.HumanoidModel<?> original) {
                if (renderer == null) renderer = new GokuArmorRenderer();
                renderer.prepForRender(entity, stack, slot, original);
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvidingSupplier() {
        return () -> renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar reg) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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

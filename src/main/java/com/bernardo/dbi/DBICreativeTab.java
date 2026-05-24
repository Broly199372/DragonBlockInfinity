package com.bernardo.dbi;

import com.bernardo.dbi.block.DBIBlocks;
import com.bernardo.dbi.item.DBIItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DragonBlockInfinity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DBICreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DragonBlockInfinity.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DBI_TAB = TABS.register("dbi_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.dragonblockinfinity"))
            .icon(() -> new ItemStack(DBIBlocks.BLOCK_PLUS_WHITE.get().asItem()))
            .displayItems((params, output) -> {
                output.accept(DBIItems.BLOCK_PLUS_WHITE.get());
                output.accept(DBIItems.BLOCK_PLUS_ORANGE.get());
                output.accept(DBIItems.BLOCK_PLUS_MAGENTA.get());
                output.accept(DBIItems.BLOCK_PLUS_LIGHT_BLUE.get());
                output.accept(DBIItems.BLOCK_PLUS_YELLOW.get());
                output.accept(DBIItems.BLOCK_PLUS_LIME.get());
                output.accept(DBIItems.BLOCK_PLUS_PINK.get());
                output.accept(DBIItems.BLOCK_PLUS_GRAY.get());
                output.accept(DBIItems.BLOCK_PLUS_LIGHT_GRAY.get());
                output.accept(DBIItems.BLOCK_PLUS_CYAN.get());
                output.accept(DBIItems.BLOCK_PLUS_PURPLE.get());
                output.accept(DBIItems.BLOCK_PLUS_BLUE.get());
                output.accept(DBIItems.BLOCK_PLUS_BROWN.get());
                output.accept(DBIItems.BLOCK_PLUS_GREEN.get());
                output.accept(DBIItems.BLOCK_PLUS_RED.get());
                output.accept(DBIItems.BLOCK_PLUS_BLACK.get());
                output.accept(DBIItems.BLOCK_NAMEKGRASS.get());
                output.accept(DBIItems.BLOCK_NAMEKDIRT.get());
                output.accept(DBIItems.DIRTY_STONE.get());
                output.accept(DBIItems.DIRTY_DIRT.get());
                output.accept(DBIItems.DIRTY_COBBLESTONE.get());
                output.accept(DBIItems.DINO_MEAT_RAW.get());
                output.accept(DBIItems.DINO_MEAT_COOKED.get());
                output.accept(DBIItems.SENZU_BEAN.get());
                
                output.accept(DBIItems.GOKU_CHESTPLATE.get());
                output.accept(DBIItems.GOKU_LEGGINGS.get());
                output.accept(DBIItems.GOKU_BOOTS.get());
            })
            .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}

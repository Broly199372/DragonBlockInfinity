package com.bernardo.dbi.client.menu;

import com.bernardo.dbi.DragonBlockInfinity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

/**
 * Central de controle de todos os menus do mod.
 * Usa sistema de páginas por ID — uma tela, vários painéis.
 * Para abrir qualquer menu: MenuControl.open(MenuControl.ID_*)
 */
public class MenuControl {

    // ── IDs DE PÁGINA ────────────────────────────────────────────
    public static final int ID_CHARACTER_CREATION  = 0;
    public static final int ID_RACE_SELECT         = 1;
    public static final int ID_COLOR_PICKER        = 2;
    public static final int ID_CLASS_SELECT        = 3;
    public static final int ID_STATS               = 10;
    public static final int ID_SKILLS              = 11;
    public static final int ID_TECHNIQUES          = 12;
    public static final int ID_TRAINING            = 13;
    public static final int ID_TRANSFORMATIONS     = 14;
    public static final int ID_ATTRIBUTES          = 15;
    public static final int ID_DRAGON_RADAR        = 20;
    public static final int ID_SCOUTER             = 21;
    public static final int ID_DIFFICULTY          = 30;
    public static final int ID_FINALIZING          = 99;

    // ── TEXTURAS GUI ─────────────────────────────────────────────
    public static final ResourceLocation GUI_ICONS_BTN =
        new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/gui/icons_btn.png");

    public static final ResourceLocation GUI_BG =
        new ResourceLocation(DragonBlockInfinity.MOD_ID, "textures/gui/gui.png");
    // ── PÁGINA ATUAL ─────────────────────────────────────────────
    /** Página que será aberta na próxima chamada de open(). */
    public static int currentPage = ID_CHARACTER_CREATION;

    // ── ABERTURA ─────────────────────────────────────────────────

    /** Abre o menu na página especificada. */
    public static void open(int pageId) {
        currentPage = pageId;
        Minecraft.getInstance().setScreen(new DBIGuiScreen(pageId));
    }

    /** Reabre o menu na mesma página atual. */
    public static void reopen() {
        open(currentPage);
    }

    /** Fecha o menu. */
    public static void close() {
        Minecraft.getInstance().setScreen(null);
    }
}

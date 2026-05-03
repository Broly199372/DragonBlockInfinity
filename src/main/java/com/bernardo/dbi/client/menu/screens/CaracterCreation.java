package com.bernardo.dbi.client.menu.screens;

import com.bernardo.dbi.client.menu.DBIGuiScreen;
import com.bernardo.dbi.client.menu.MenuControl;
import com.bernardo.dbi.client.widget.SmallArrowBtn;
import com.bernardo.dbi.core.PlayerPreview;
import com.bernardo.dbi.core.race.Race;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/** Tela de criação de personagem com seletor de raça e color picker. */
public class CaracterCreation extends DBIGuiScreen {

    // ── RAÇA ──────────────────────────────────────────────────────
    private static final Race[] RACES = Race.values();
    private int raceIndex = 0;

    // ── CORES (3 slots) ───────────────────────────────────────────
    private int[] slotColors = { 0xFFFFFFFF, 0xFF888888, 0xFF333333 };
    private int activeSlot = -1;

    // ── COLOR PICKER ──────────────────────────────────────────────
    private boolean colorPickerOpen = false;
    private float hue = 0f;
    private float saturation = 1f;
    private float brightness = 1f;
    private boolean draggingWheel = false;
    private boolean draggingBright = false;

    public CaracterCreation() {
        super(MenuControl.ID_CHARACTER_CREATION);
    }

    @Override
    protected void init() {
        super.init();
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Botão ← usando SmallArrowBtn
        this.addRenderableWidget(new SmallArrowBtn(
            cx + 58, cy - 122,
            SmallArrowBtn.Direction.LEFT,
            () -> {
                raceIndex = (raceIndex - 1 + RACES.length) % RACES.length;
                colorPickerOpen = false;
            }
        ));

        // Botão → usando SmallArrowBtn
        this.addRenderableWidget(new SmallArrowBtn(
            cx + 88, cy - 122,
            SmallArrowBtn.Direction.RIGHT,
            () -> {
                raceIndex = (raceIndex + 1) % RACES.length;
                colorPickerOpen = false;
            }
        ));
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);

        int cx = this.width / 2;
        int cy = this.height / 2;

        // Fundo GUI
        gui.blit(MenuControl.GUI_BG, cx - 128, cy - 128, 0, 0, 256, 256, 256, 256);

        // Preview do player / raça
        Race current = RACES[raceIndex];
        if (current.hasTexture()) {
            gui.blit(current.getTexture(), cx - 40, cy - 80, 0, 0, 64, 64, 64, 64);
        } else {
            PlayerPreview.render(gui, cx - 40, cy + 20, 40, 0f);
        }

        // Nome da raça — entre os botões
        gui.drawCenteredString(this.font,
            Component.literal(current.getDisplayName()),
            cx + 74, cy - 113, 0xFFFFFF);

        // 3 quadrados de cor — bem abaixo do nome da raça
        int slotY = cy - 88;
        int slotSize = 14;
        int slotStartX = cx + 52;
        for (int i = 0; i < 3; i++) {
            int sx = slotStartX + i * (slotSize + 5);
            // fill da cor
            gui.fill(sx, slotY, sx + slotSize, slotY + slotSize, slotColors[i]);
            // borda branca
            gui.fill(sx - 1, slotY - 1, sx + slotSize + 1, slotY,              0xFFFFFFFF);
            gui.fill(sx - 1, slotY + slotSize, sx + slotSize + 1, slotY + slotSize + 1, 0xFFFFFFFF);
            gui.fill(sx - 1, slotY - 1, sx,              slotY + slotSize + 1, 0xFFFFFFFF);
            gui.fill(sx + slotSize, slotY - 1, sx + slotSize + 1, slotY + slotSize + 1, 0xFFFFFFFF);
        }

        // Color picker (só slot 0)
        if (colorPickerOpen && activeSlot == 0) {
            renderColorPicker(gui, mouseX, mouseY, cx, cy);
        }

        // Título
        gui.drawCenteredString(this.font,
            Component.translatable("screen.dragonblockinfinity.character_creation"),
            cx, cy - 120, 0xFFFFFF);

        super.render(gui, mouseX, mouseY, partialTick);
    }

    private void renderColorPicker(GuiGraphics gui, int mouseX, int mouseY, int cx, int cy) {
        int px = cx - 120;
        int py = cy - 50;
        int size = 80;

        // Fundo escuro
        gui.fill(px - 4, py - 4, px + size + 54, py + size + 28, 0xCC000000);

        // Gradiente de hue
        for (int s = 0; s < size; s++) {
            float h = (float) s / size;
            gui.fill(px + s, py, px + s + 1, py + size, hsvToArgb(h, saturation, brightness));
        }

        // Indicador de hue
        int hueX = px + (int)(hue * size);
        gui.fill(hueX, py - 2, hueX + 2, py + size + 2, 0xFFFFFFFF);

        // Barra de brilho
        int barY = py + size + 8;
        for (int b = 0; b < size; b++) {
            float bv = (float) b / size;
            gui.fill(px + b, barY, px + b + 1, barY + 10, hsvToArgb(hue, saturation, bv));
        }

        // Indicador de brilho
        int brightX = px + (int)(brightness * size);
        gui.fill(brightX, barY - 2, brightX + 2, barY + 12, 0xFFFFFFFF);

        // Preview da cor atual
        gui.fill(px + size + 8, py, px + size + 44, py + 36, slotColors[0]);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cx = this.width / 2;
        int cy = this.height / 2;

        // Clique nos slots
        int slotY = cy - 88;
        int slotSize = 14;
        int slotStartX = cx + 52;
        for (int i = 0; i < 3; i++) {
            int sx = slotStartX + i * (slotSize + 5);
            if (mouseX >= sx && mouseX <= sx + slotSize &&
                mouseY >= slotY && mouseY <= slotY + slotSize) {
                if (i == 0) {
                    activeSlot = 0;
                    colorPickerOpen = !colorPickerOpen;
                }
                return true;
            }
        }

        // Clique no color picker
        if (colorPickerOpen && activeSlot == 0) {
            int px = cx - 120;
            int py = cy - 50;
            int size = 80;

            if (mouseX >= px && mouseX <= px + size &&
                mouseY >= py && mouseY <= py + size) {
                hue = (float)((mouseX - px) / size);
                hue = Math.max(0f, Math.min(1f, hue));
                slotColors[0] = hsvToArgb(hue, saturation, brightness);
                draggingWheel = true;
                return true;
            }

            int barY = py + size + 8;
            if (mouseX >= px && mouseX <= px + size &&
                mouseY >= barY && mouseY <= barY + 10) {
                brightness = (float)((mouseX - px) / size);
                brightness = Math.max(0f, Math.min(1f, brightness));
                slotColors[0] = hsvToArgb(hue, saturation, brightness);
                draggingBright = true;
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int cx = this.width / 2;
        int cy = this.height / 2;
        int px = cx - 120;
        int py = cy - 50;
        int size = 80;

        if (draggingWheel) {
            hue = (float)((mouseX - px) / size);
            hue = Math.max(0f, Math.min(1f, hue));
            slotColors[0] = hsvToArgb(hue, saturation, brightness);
            return true;
        }
        if (draggingBright) {
            brightness = (float)((mouseX - px) / size);
            brightness = Math.max(0f, Math.min(1f, brightness));
            slotColors[0] = hsvToArgb(hue, saturation, brightness);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingWheel = false;
        draggingBright = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            if (colorPickerOpen) { colorPickerOpen = false; return true; }
            MenuControl.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private static int hsvToArgb(float h, float s, float v) {
        float[] rgb = java.awt.Color.getHSBColor(h, s, v).getRGBColorComponents(null);
        int r = (int)(rgb[0] * 255);
        int g = (int)(rgb[1] * 255);
        int b = (int)(rgb[2] * 255);
        return (0xFF << 24) | (r << 16) | (g << 8) | b;
    }
}

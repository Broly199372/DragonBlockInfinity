package com.bernardo.dbi.core;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;

/** Renderiza o player em 3D dentro de uma GUI. */
public class PlayerPreview {

    public static void render(GuiGraphics gui, int x, int y, int scale, float rotation) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        RenderSystem.setProjectionMatrix(
            new org.joml.Matrix4f().ortho(
                -mc.getWindow().getGuiScaledWidth() / 2f,
                mc.getWindow().getGuiScaledWidth() / 2f,
                mc.getWindow().getGuiScaledHeight() / 2f,
                -mc.getWindow().getGuiScaledHeight() / 2f,
                0.1f, 1000f
            ),
            com.mojang.blaze3d.vertex.VertexSorting.ORTHOGRAPHIC_Z
        );

        var pose = gui.pose();
        pose.pushPose();
        pose.translate(x, y, 50);
        pose.mulPose(new Quaternionf().rotateZ((float) Math.PI));
        pose.scale(scale, scale, scale);

        var dispatcher = mc.getEntityRenderDispatcher();
        dispatcher.setRenderShadow(false);
        var source = mc.renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() ->
            dispatcher.render(player, 0, 0, 0, 0f, 1f, pose, source, 0xF000F0)
        );
        source.endBatch();
        dispatcher.setRenderShadow(true);
        pose.popPose();
    }
}

package com.bernardo.dbi.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

/** Provider de partículas para carnes de dino. */
public class DinoMeatParticleProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet sprites;

    public DinoMeatParticleProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level,
            double x, double y, double z, double dx, double dy, double dz) {
        return new DinoMeatParticle(level, x, y, z, dx, dy, dz, sprites);
    }

    static class DinoMeatParticle extends TextureSheetParticle {

        DinoMeatParticle(ClientLevel level, double x, double y, double z,
                         double dx, double dy, double dz, SpriteSet sprites) {
            super(level, x, y, z, dx, dy, dz);
            this.pickSprite(sprites);
            this.lifetime = 12;
            this.gravity = 0.9f;
            this.scale(0.5f);
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
        }
    }
}

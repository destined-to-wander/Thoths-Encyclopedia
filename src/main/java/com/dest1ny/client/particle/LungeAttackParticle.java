package com.dest1ny.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class LungeAttackParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected LungeAttackParticle(ClientWorld level,
                                  double xCoord, double yCoord, double zCoord,
                                  double Scale, SpriteProvider spriteSet) {
        super(level, xCoord, yCoord, zCoord, 0.0, 0.0, 0.0);
        this.spriteProvider = spriteSet;
        this.maxAge = 4;
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.red = f;
        this.green = f;
        this.blue = f;
        this.scale = 1.0F - (float)Scale * 0.5F;
        this.setSpriteForAge(spriteProvider);
        /*
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.scale = 1.0f - (float)Scale * 0.5f;;
        this.maxAge = 4;
        this.setSpriteForAge(spriteSet);

        this.spriteProvider = spriteSet;

        float f;
        this.red = f = this.random.nextFloat() * 0.6f + 0.4f;
        this.green = f;
        this.blue = f;

         */
    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternionf quaternionf;
        if (this.angle == 0.0F) {
            quaternionf = camera.getRotation();
        } else {
            quaternionf = new Quaternionf(camera.getRotation());
            quaternionf.rotateZ(MathHelper.lerp(tickDelta, this.prevAngle, this.angle));
        }

        Vector3f[] vector3fs = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float i = this.getSize(tickDelta);

        for (int j = 0; j < 4; j++) {
            Vector3f vector3f = vector3fs[j];
            vector3f.rotate(quaternionf);
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }

        float k = this.getMinU();
        float l = this.getMaxU();
        float m = this.getMinV();
        float n = this.getMaxV();
        int o = this.getBrightness(tickDelta);
        vertexConsumer.vertex((double)vector3fs[0].x(), (double)vector3fs[0].y(), (double)vector3fs[0].z())
                .texture(l, n)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(o)
                .next();
        vertexConsumer.vertex((double)vector3fs[1].x(), (double)vector3fs[1].y(), (double)vector3fs[1].z())
                .texture(l, m)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(o)
                .next();
        vertexConsumer.vertex((double)vector3fs[2].x(), (double)vector3fs[2].y(), (double)vector3fs[2].z())
                .texture(k, m)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(o)
                .next();
        vertexConsumer.vertex((double)vector3fs[3].x(), (double)vector3fs[3].y(), (double)vector3fs[3].z())
                .texture(k, n)
                .color(this.red, this.green, this.blue, this.alpha)
                .light(o)
                .next();
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteset) {
            this.sprites = spriteset;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld level, double x, double y, double z, double dx, double dy, double dz) {
            return new LungeAttackParticle(level, x, y, z,dx, this.sprites);
        }

        /*
        @Override
        public /* synthetic *//* Particle createParticle(ParticleEffect particleEffect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return this.createParticle((DefaultParticleType)particleEffect, clientWorld, d, e, f, g, h, i);
        }*/
    }
}

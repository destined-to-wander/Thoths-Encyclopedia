package com.destinedtowander.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends ProjectileEntity {
    public TridentEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    private boolean dealtDamage;

    @Inject(at = @At("HEAD"), method = "tick")
    public void loyalty(CallbackInfo info) {
        if (this.getPos().y <= -128){
            this.dealtDamage = true;
        }
    }

    @Shadow
    private ItemStack tridentStack;

    @ModifyVariable(method = "onEntityHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F",
                    shift = At.Shift.AFTER/**/),
            ordinal = 0
    )
    public float impaling(float f, @Local(argsOnly = true) EntityHitResult entityHitResult){
        if (FabricLoader.getInstance().isModLoaded("impaled")) return f;
        Entity entity = entityHitResult.getEntity();
        int impalingLevel = EnchantmentHelper.getLevel(Enchantments.IMPALING,this.tridentStack);
        f -= Enchantments.IMPALING.getAttackDamage(impalingLevel,((LivingEntity)entity).getGroup());
        if (entity.isWet() && impalingLevel > 0){
            f += impalingLevel;
        }
        return f;
    }

}
package com.destinedtowander.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
	int riptideLevelForGrace = 4; // for config when I eventually add it

	public LivingEntityMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
	}

	@Shadow
	public abstract ItemStack getStackInHand(Hand hand);

	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@Inject(at = @At("HEAD"), method = "tick")
	private void riptide(CallbackInfo info) {
		ItemStack stack;
		if (this.getStackInHand(Hand.MAIN_HAND).getItem() != Items.ENCHANTED_BOOK){
			stack = this.getStackInHand(Hand.MAIN_HAND);
		} else if (this.getStackInHand(Hand.OFF_HAND).getItem() != Items.ENCHANTED_BOOK){
			stack = this.getStackInHand(Hand.OFF_HAND);
		} else {
			return;
		}

		int riptideLevel = EnchantmentHelper.getLevel(Enchantments.RIPTIDE, stack);
		if (riptideLevel >= riptideLevelForGrace && !this.hasStatusEffect(StatusEffects.DOLPHINS_GRACE)){
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE,-1,1,true,false));
		}
	}
}
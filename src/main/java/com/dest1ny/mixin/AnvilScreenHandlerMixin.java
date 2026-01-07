package com.dest1ny.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static net.minecraft.screen.AnvilScreenHandler.getNextCost;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	/*
	@Override
	protected boolean canTakeOutput(PlayerEntity player, boolean present) {
		return false;
	}

	@Override
	protected void onTakeOutput(PlayerEntity player, ItemStack stack) {

	}

	@Override
	protected boolean canUse(BlockState state) {
		return false;
	}
	 */

	@Shadow
	private Property levelCost;

	@Shadow
	private int repairItemUsage;

	@Shadow
	private String newItemName;

	@Inject(at = @At("HEAD"),method = "updateResult", cancellable = true)
	public void updateResult(CallbackInfo ci) {
		ci.cancel();

		AnvilScreenHandler handler = (AnvilScreenHandler)(Object) this;
		ItemStack itemStack = this.input.getStack(0);
		this.levelCost.set(1);
		int i = 0;
		int j = 0;
		int k = 0;
		if (itemStack.isEmpty()) {
			this.output.setStack(0, ItemStack.EMPTY);
			this.levelCost.set(0);
		} else {
			ItemStack itemStack2 = itemStack.copy();
			ItemStack itemStack3 = this.input.getStack(1);
			Map<Enchantment, Integer> map = EnchantmentHelper.get(itemStack2);
			j += itemStack.getRepairCost() + (itemStack3.isEmpty() ? 0 : itemStack3.getRepairCost());
			this.repairItemUsage = 0;
			if (!itemStack3.isEmpty()) {
				boolean bl = itemStack3.isOf(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantmentNbt(itemStack3).isEmpty();
				if (itemStack2.isDamageable() && itemStack2.getItem().canRepair(itemStack, itemStack3)) {
					int repairVal;
					if (itemStack3.isOf(Items.NETHERITE_INGOT) || itemStack3.isOf(Items.HEART_OF_THE_SEA)){
						repairVal = itemStack2.getMaxDamage();
					} else {
						repairVal = (int) Math.ceil(itemStack2.getMaxDamage() / 4.0);
					}

					int l = Math.min(itemStack2.getDamage(),repairVal);
					if (l <= 0) {
						this.output.setStack(0, ItemStack.EMPTY);
						this.levelCost.set(0);
						return;
					}

					int m;
					for (m = 0; l > 0 && m < itemStack3.getCount(); m++) {
						int n = itemStack2.getDamage() - l;
						itemStack2.setDamage(n);
						l = Math.min(itemStack2.getDamage(),repairVal);
					}

					i += m;
					this.repairItemUsage = m;
				} else {
					if (!bl && (!itemStack2.isOf(itemStack3.getItem()) || !itemStack2.isDamageable())) {
						this.output.setStack(0, ItemStack.EMPTY);
						this.levelCost.set(0);
						return;
					}

					if (itemStack2.isDamageable() && !bl) {
						int lx = itemStack.getMaxDamage() - itemStack.getDamage();
						int m = itemStack3.getMaxDamage() - itemStack3.getDamage();
						int n = itemStack2.getMaxDamage() * 12 / 100;
						int o = lx + m + n;
						int p = itemStack2.getMaxDamage() - o;
						if (p < 0) {
							p = 0;
						}

						if (p < itemStack2.getDamage()) {
							itemStack2.setDamage(p);
							i += 2;
						}
					}

					Map<Enchantment, Integer> map2 = EnchantmentHelper.get(itemStack3);
					boolean bl2 = false;
					boolean bl3 = false;

					for (Enchantment enchantment : map2.keySet()) {
						if (enchantment != null) {
							int q = (Integer)map.getOrDefault(enchantment, 0);
							int r = (Integer)map2.get(enchantment);
							r = q == r ? r + 1 : Math.max(r, q);
							boolean bl4 = enchantment.isAcceptableItem(itemStack);
							if (this.player.getAbilities().creativeMode || itemStack.isOf(Items.ENCHANTED_BOOK)) {
								bl4 = true;
							}

							for (Enchantment enchantment2 : map.keySet()) {
								if (enchantment2 != enchantment && !enchantment.canCombine(enchantment2)) {
									bl4 = false;
									i++;
								}
							}

							if (!bl4) {
								bl3 = true;
							} else {
								bl2 = true;
								/* If you somehow manage to get past max level you deserve to keep it
								if (r > enchantment.getMaxLevel()) {
									r = enchantment.getMaxLevel();
								}
								 */

								map.put(enchantment, r);
								int s = 0;
								switch (enchantment.getRarity()) {
									case COMMON:
										s = 1;
										break;
									case UNCOMMON:
										s = 2;
										break;
									case RARE:
										s = 4;
										break;
									case VERY_RARE:
										s = 8;
								}

								if (bl) {
									s = Math.max(1, s / 2);
								}

								i += s * r;
								if (itemStack.getCount() > 1) {
									i = -100;
								}
							}
						}
					}

					if (bl3 && !bl2) {
						this.output.setStack(0, ItemStack.EMPTY);
						this.levelCost.set(0);
						return;
					}
				}
			}

			if (this.newItemName != null && !Util.isBlank(this.newItemName)) {
				if (!this.newItemName.equals(itemStack.getName().getString())) {
					k = 1;
					itemStack2.setCustomName(Text.literal(this.newItemName));
				}
			} else if (itemStack.hasCustomName()) {
				k = 1;
				itemStack2.removeCustomName();
			}
			i += i < k ? 1 : 0;

			this.levelCost.set(j + i);
			if (i <= 0) {
				itemStack2 = ItemStack.EMPTY;
			}

			if (this.levelCost.get() <= 0) {
				itemStack2 = ItemStack.EMPTY;
			}

			if (!itemStack2.isEmpty()) {
				int t = itemStack2.getRepairCost();
				if (!itemStack3.isEmpty() && t < itemStack3.getRepairCost()) {
					t = itemStack3.getRepairCost();
				}
				int finalcost = t;

				//itemStack2.setRepairCost(finalcost);
				EnchantmentHelper.set(map, itemStack2);
			}

			this.output.setStack(0, itemStack2);
			handler.sendContentUpdates();
		}
	}
}

package com.destinedtowander.common.items;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.cca.WanderersComponent;
import com.destinedtowander.common.index.ModComponents;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class WanderersSwordItem extends SwordItem implements Vanishable {
    protected static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567");
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("a31c8afc-a716-425d-89cd-0d373380e6e7");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public WanderersSwordItem(Settings settings) {
        super(WandererToolMaterial.INSTANCE, 8, -2.6F, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 7.5F, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.7, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_REACH_MODIFIER_ID, "Weapon modifier", 0.50F, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", 1.25F, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return 12.0F;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (hand == Hand.OFF_HAND) return TypedActionResult.fail(itemStack);
        else {
            user.setCurrentHand(hand);
            if (!user.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
                WanderersComponent.get(user).startParry();
                user.getItemCooldownManager().set(itemStack.getItem(), 40);
            }
            WanderersComponent.get(user).setBlocking(true);
            return TypedActionResult.consume(itemStack);
        }
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            ModComponents.WANDERER_COMPONENT.get(player).setBlocking(false);
            ModComponents.WANDERER_COMPONENT.get(player).stopParry();
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public int getMaxUseTime(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.thothpedia.wanderers_sword.desc").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.thothpedia.wanderers_sword.parry_desc").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}
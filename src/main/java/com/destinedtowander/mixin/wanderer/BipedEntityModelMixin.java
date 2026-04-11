package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.index.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> {
    @Shadow
    @Final
    public ModelPart body;
    @Shadow
    @Final
    public ModelPart head;

    public BipedEntityModelMixin() {
    }

    @Shadow
    protected abstract Arm getPreferredArm(T var1);

    @Shadow
    protected abstract ModelPart getArm(Arm var1);

    @WrapOperation(
        method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;isUsingItem()Z",
            ordinal = 0
        )
    )
    public boolean dualBlock(LivingEntity living, Operation<Boolean> original) {
        return !living.getActiveItem().isOf(ModItems.WANDERERS_SWORD) && original.call(living);
    }

    /*
    @Inject(
        method = {"animateArms"},
        at = {@At("TAIL")},
        cancellable = true
    )
    protected void twoHanding(T entity, float animationProgress, CallbackInfo ci) {
        ItemStack stack = entity.getMainHandStack();
        if (stack.isOf(ModItems.WANDERERS_SWORD) && !(this.handSwingProgress <= 0.0F)) {
            Arm arm = this.getPreferredArm(entity) == Arm.RIGHT ? Arm.LEFT : Arm.RIGHT;
            ModelPart modelPart = this.getArm(arm);
            float f = 1.0F - this.handSwingProgress;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float g = MathHelper.sin(f * (float)Math.PI);
            float h = MathHelper.sin(this.handSwingProgress * (float)Math.PI) * -(this.head.pitch - 0.7F) * 0.75F;
            modelPart.pitch -= g * 1.2F + h;
            modelPart.yaw += this.body.yaw * 2.0F;
            modelPart.roll += MathHelper.sin(this.handSwingProgress * (float)Math.PI) * -0.4F;
        }
    }//*/
}

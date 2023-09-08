package io.github.tbmstudios.cartocraft.mixin;

import io.github.tbmstudios.cartocraft.common.item.api.ISpyglassLike;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isUsingSpyglass", at = @At("HEAD"), cancellable = true)
    private void inject$isUsingSpyglass(CallbackInfoReturnable<Boolean> cir) {
        if (isUsingItem() && getActiveItem().getItem() instanceof ISpyglassLike) cir.setReturnValue(true);
    }
}

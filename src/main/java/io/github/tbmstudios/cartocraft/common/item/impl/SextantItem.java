package io.github.tbmstudios.cartocraft.common.item.impl;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SextantItem extends Item {
    public SextantItem() {
        super(new Settings().maxCount(1));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 24000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.playSound(SoundEvents.ITEM_SPYGLASS_USE, 1.0F, 1.0F);
        return ItemUsage.consumeHeldItem(world, player, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;
        if (world.isClient) return;
        if (!world.getRegistryKey().equals(World.OVERWORLD)) {
            player.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0F, 1.0F);
            return;
        }
        final int x = player.getBlockX();
        final int z = player.getBlockZ();
        final double skyAngle = Math.round(Math.toDegrees(world.getSkyAngleRadians(.0F)));
        float yaw = MathHelper.wrapDegrees(player.getYaw());
        final double skyAngleN = (skyAngle + 180) % 360;
        float pitch = player.getPitch();
        if (yaw > 0) pitch = Math.round(pitch) + 90;
        if (yaw < 0) pitch = Math.round(Math.abs(pitch - 90) + 180);
        yaw = Math.round(yaw);
        if (yaw >= 88 && yaw <= 92 && pitch >= skyAngle - 2 && pitch <= skyAngle + 2 || yaw >= -92 && yaw <= -88 && pitch >= skyAngle - 2 && pitch <= skyAngle + 2 ||
                yaw >= 88 && yaw <= 92 && pitch >= skyAngleN - 2 && pitch <= skyAngleN + 2 || yaw >= -92 && yaw <= -88 && pitch >= skyAngleN - 2 && pitch <= skyAngleN + 2) {
            player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
            player.sendMessage(Text.translatable("msg.cartocraft.sextant.position", x, z), true);
        } else player.playSound(SoundEvents.ITEM_SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.cartocraft.sextant"));
    }
}

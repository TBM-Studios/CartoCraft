package io.github.tbmstudios.cartocraft.common.item.impl;

import io.github.tbmstudios.cartocraft.common.item.api.ISpyglassLike;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TerratantItem extends Item implements ISpyglassLike {
    public TerratantItem() {
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
        if (!world.isClient) world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ITEM_SPYGLASS_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
        return ItemUsage.consumeHeldItem(world, player, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        if (world.isClient || !(user instanceof PlayerEntity player)) return;
        if (!world.getRegistryKey().equals(World.OVERWORLD)) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_SPYGLASS_STOP_USING, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return;
        }
        final double skyAngle = Math.round(Math.toDegrees(world.getSkyAngleRadians(.0F)));
        float yaw = MathHelper.wrapDegrees(player.getYaw());
        final double skyAngleN = (skyAngle + 180) % 360;
        float pitch = player.getPitch();
        if (yaw > 0) pitch = Math.round(pitch) + 90;
        if (yaw < 0) pitch = Math.round(Math.abs(pitch - 90) + 180);
        yaw = Math.round(yaw);
        if (yaw >= 88 && yaw <= 92 && pitch >= skyAngle - 2 && pitch <= skyAngle + 2 || yaw >= -92 && yaw <= -88 && pitch >= skyAngle - 2 && pitch <= skyAngle + 2 ||
                yaw >= 88 && yaw <= 92 && pitch >= skyAngleN - 2 && pitch <= skyAngleN + 2 || yaw >= -92 && yaw <= -88 && pitch >= skyAngleN - 2 && pitch <= skyAngleN + 2) {
            player.getItemCooldownManager().set(this, 60);
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 1.0F, 1.0F);
            final int distSeaLevel = player.getBlockY() - 63;
            player.sendMessage(
                    Text.translatable("msg.cartocraft.position", player.getBlockX(), player.getBlockZ())
                            .append("  |  ")
                            .append(Text.translatable("msg.cartocraft.sealevel", distSeaLevel))
                    , true);
        } else world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ITEM_SPYGLASS_STOP_USING, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.cartocraft.terratant"));
    }
}

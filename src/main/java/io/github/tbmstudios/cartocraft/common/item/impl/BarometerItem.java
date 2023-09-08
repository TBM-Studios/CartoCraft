package io.github.tbmstudios.cartocraft.common.item.impl;

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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarometerItem extends Item {
    public BarometerItem() {
        super(new Settings().maxCount(1));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 25;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        if (world.isClient || !(user instanceof PlayerEntity player)) return;
        player.getItemCooldownManager().set(this, 60);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLOCK_LAVA_POP, SoundCategory.PLAYERS, .8F, 1.2F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1.0F, .1F);
        final int distSeaLevel = player.getBlockY() - 63;
        player.sendMessage(Text.translatable("msg.cartocraft.sealevel", distSeaLevel));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.cartocraft.barometer"));
    }
}

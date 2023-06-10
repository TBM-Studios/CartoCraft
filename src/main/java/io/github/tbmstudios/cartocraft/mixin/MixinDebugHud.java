package io.github.tbmstudios.cartocraft.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Shadow
    @Final
    private MinecraftClient client;
    private static final List<String> blackList = List.of("XYZ:", "Block:", "Chunk:", "Facing:", "CH S:", "SH S:", "Chunks[");

    @Redirect(method = "getLeftText", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private boolean redirect$getLeftText$1(List<String> instance, Object e) {
        if (!(e instanceof String s)) return true;
        final PlayerEntity p = MinecraftClient.getInstance().player;
        if (p == null) return instance.add(s);
        for (final var prefix : blackList) if (s.startsWith(prefix) && !p.getAbilities().creativeMode) return true;
        return instance.add(s);
    }

    @Redirect(method = "getLeftText", at = @At(value = "INVOKE",
            target = "Lcom/google/common/collect/Lists;newArrayList([Ljava/lang/Object;)Ljava/util/ArrayList;", remap = false))
    private ArrayList<String> inject$getLeftText(Object[] elements) {
        if (!(elements instanceof String[] as)) return Lists.newArrayList();
        final ArrayList<String> raw = Lists.newArrayList(as);
        if (!isCreative()) raw.removeIf(s -> s.startsWith("Chunks["));
        final Entity camera = this.client.getCameraEntity();
        if (camera == null) return raw;
        final BlockPos pos = camera.getBlockPos();
        raw.add(String.format(Locale.ROOT, "Chunk-relative: %d %d %d", pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15));
        return raw;
    }

    private static boolean isCreative() {
        final PlayerEntity p = MinecraftClient.getInstance().player;
        if (p == null) return false;
        return p.getAbilities().creativeMode;
    }
}

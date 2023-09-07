package io.github.tbmstudios.cartocraft.common;

import io.github.tbmstudios.cartocraft.common.item.init.ItemRef;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;

public class CartoCraft implements ModInitializer {
    public static final String MODID = "cartocraft";

    @Override
    public void onInitialize() {
        ItemRef.init();
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(((group, entries) -> {
            Registries.ITEM_GROUP.getKey(group).ifPresent(k -> {
                if (k.equals(ItemGroups.TOOLS)) {
                    entries.add(ItemRef.SEXTANT.asItem().getDefaultStack());
                }
            });
        }));
    }
}

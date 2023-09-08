package io.github.tbmstudios.cartocraft.common.item.init;

import io.github.tbmstudios.cartocraft.common.CartoCraft;
import io.github.tbmstudios.cartocraft.common.item.impl.SextantItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ItemRef implements ItemConvertible {
    SEXTANT(new SextantItem());
    private final Item item;

    ItemRef(Item item) {
        this.item = item;
    }

    public static void init() {
        final ItemRef[] refs = ItemRef.values();
        for (final ItemRef ref : refs) Registry.register(Registries.ITEM, ref.getId(), ref.asItem());
    }

    public Identifier getId() {
        return new Identifier(CartoCraft.MODID, this.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public Item asItem() {
        return item;
    }
}

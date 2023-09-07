package io.github.tbmstudios.cartocraft.common.item.init;

import io.github.tbmstudios.cartocraft.common.CartoCraft;
import io.github.tbmstudios.cartocraft.common.item.impl.SextantItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public enum ItemRef implements ItemConvertible {
    SEXTANT("sextant", new SextantItem());
    private final Identifier id;
    private final Item item;

    ItemRef(String id, Item item) {
        this.id = new Identifier(CartoCraft.MODID, id);
        this.item = item;
    }

    public static void init() {
        final ItemRef[] refs = ItemRef.values();
        for (final ItemRef ref : refs) Registry.register(Registries.ITEM, ref.getId(), ref.asItem());
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public Item asItem() {
        return item;
    }
}

package io.github.tbmstudios.cartocraft.common.item.init;

import io.github.tbmstudios.cartocraft.common.CartoCraft;
import io.github.tbmstudios.cartocraft.common.item.impl.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Locale;
import java.util.function.Supplier;

public enum ItemRef implements Supplier<Item> {
    SEXTANT(new SextantItem()),
    BAROMETER(new BarometerItem()),
    MULTI_KATOMETER(new MultiKatometerItem()),
    TERRATANT(new TerratantItem()),
    KATOMETER(new KatometerItem()),
    TELOSMETER(new TelosmeterItem()),
    VIATOR(new ViatorItem());
    private final Item item;

    ItemRef(Item item) {
        this.item = item;
    }

    public static void init() {
        final ItemRef[] refs = ItemRef.values();
        for (final ItemRef ref : refs) Registry.register(Registries.ITEM, ref.getId(), ref.get());
    }

    public Identifier getId() {
        return new Identifier(CartoCraft.MODID, this.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public Item get() {
        return item;
    }
}

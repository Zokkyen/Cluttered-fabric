package net.neoforged.neoforge.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class DeferredItem<T extends Item> extends DeferredHolder<Item, T> implements ItemLike {
    DeferredItem(ResourceLocation id, Supplier<? extends T> supplier) {
        super(id, supplier);
    }

    @Override
    public Item asItem() {
        return get();
    }
}

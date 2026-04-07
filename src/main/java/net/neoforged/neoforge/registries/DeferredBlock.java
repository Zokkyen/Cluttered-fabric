package net.neoforged.neoforge.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class DeferredBlock<T extends Block> extends DeferredHolder<Block, T> implements ItemLike {
    DeferredBlock(ResourceLocation id, Supplier<? extends T> supplier) {
        super(id, supplier);
    }

    @Override
    public Item asItem() {
        return get().asItem();
    }
}

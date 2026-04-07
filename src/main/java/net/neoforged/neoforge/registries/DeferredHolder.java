package net.neoforged.neoforge.registries;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class DeferredHolder<R, T extends R> implements Supplier<T> {
    private final ResourceLocation id;
    private final Supplier<? extends T> supplier;
    private T value;

    DeferredHolder(ResourceLocation id, Supplier<? extends T> supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public T get() {
        return getOrCreate();
    }

    T getOrCreate() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }
}

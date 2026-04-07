package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class DeferredRegister<T> {
    private final ResourceKey<? extends Registry<T>> registryKey;
    private final String namespace;
    private final List<DeferredHolder<T, ? extends T>> entries = new ArrayList<>();
    private boolean registered;

    protected DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        this.registryKey = registryKey;
        this.namespace = namespace;
    }

    public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        return new DeferredRegister<>(registryKey, namespace);
    }

    public static Blocks createBlocks(String namespace) {
        return new Blocks(namespace);
    }

    public static Items createItems(String namespace) {
        return new Items(namespace);
    }

    public <I extends T> DeferredHolder<T, I> register(String name, Supplier<? extends I> supplier) {
        DeferredHolder<T, I> holder = new DeferredHolder<>(id(name), supplier);
        return track(holder);
    }

    public void register(IEventBus ignoredEventBus) {
        if (registered) {
            return;
        }

        Registry<T> registry = resolveRegistry();
        for (DeferredHolder<T, ? extends T> holder : entries) {
            T value = holder.getOrCreate();
            registerValue(registry, holder.getId(), value);
        }

        registered = true;
    }

    protected final ResourceLocation id(String name) {
        return Objects.requireNonNull(
            ResourceLocation.fromNamespaceAndPath(
                Objects.requireNonNull(namespace, "namespace"),
                Objects.requireNonNull(name, "name")
            ),
            "resource location"
        );
    }

    protected final <I extends T, H extends DeferredHolder<T, I>> H track(H holder) {
        entries.add(holder);
        return holder;
    }

    @SuppressWarnings("unchecked")
    private Registry<T> resolveRegistry() {
        ResourceLocation registryLocation = Objects.requireNonNull(registryKey.location(), "registry location");
        Registry<?> registry = BuiltInRegistries.REGISTRY.get(registryLocation);
        if (registry == null) {
            throw new IllegalStateException("Unknown registry: " + registryLocation);
        }
        return (Registry<T>) registry;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T> void registerValue(Registry<T> registry, ResourceLocation id, T value) {
        Registry<T> nonNullRegistry = Objects.requireNonNull(registry, "registry");
        ResourceLocation nonNullId = Objects.requireNonNull(id, "id");
        T nonNullValue = Objects.requireNonNull(value, "value");
        Registry.register((Registry) nonNullRegistry, nonNullId, nonNullValue);
    }

    public static final class Blocks extends DeferredRegister<Block> {
        private Blocks(String namespace) {
            super(Registries.BLOCK, namespace);
        }

        public <B extends Block> DeferredBlock<B> register(String name, Supplier<? extends B> supplier) {
            return track(new DeferredBlock<>(id(name), supplier));
        }
    }

    public static final class Items extends DeferredRegister<Item> {
        private Items(String namespace) {
            super(Registries.ITEM, namespace);
        }

        public <I extends Item> DeferredItem<I> register(String name, Supplier<? extends I> supplier) {
            return track(new DeferredItem<>(id(name), supplier));
        }
    }
}

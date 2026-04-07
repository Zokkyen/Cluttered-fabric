package net.redchujelly.cluttered.fabric.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;
import net.redchujelly.cluttered.fabric.ClutteredFabric;

public final class ClutteredSoundRegistry {
    public static final SoundEvent POLAROID_FLASH = registerVariableRange("polaroid_flash");

    private ClutteredSoundRegistry() {
    }

    public static void register() {
        ClutteredFabric.LOGGER.info("Registering Fabric sounds");
    }

    private static SoundEvent registerVariableRange(String path) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ClutteredFabric.MODID, path);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}

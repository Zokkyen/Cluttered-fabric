package net.redchujelly.cluttered.fabric.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;
import net.redchujelly.cluttered.fabric.ClutteredFabric;

import java.util.Objects;

public final class ClutteredSoundRegistry {
    public static final SoundEvent POLAROID_FLASH = registerVariableRange("polaroid_flash");

    private ClutteredSoundRegistry() {
    }

    public static void register() {
        ClutteredFabric.LOGGER.info("Registering Fabric sounds");
    }

    private static SoundEvent registerVariableRange(String path) {
        String safePath = Objects.requireNonNull(path, "path");
        ResourceLocation id = Objects.requireNonNull(
                ResourceLocation.fromNamespaceAndPath(Objects.requireNonNull(ClutteredFabric.MODID, "mod id"), safePath),
                "sound id"
        );
        SoundEvent soundEvent = Objects.requireNonNull(SoundEvent.createVariableRangeEvent(id), "sound event");
        return Registry.register(Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT, "sound event registry"), id, soundEvent);
    }
}

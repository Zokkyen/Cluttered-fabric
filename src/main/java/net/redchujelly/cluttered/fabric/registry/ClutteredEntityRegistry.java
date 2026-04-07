package net.redchujelly.cluttered.fabric.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.redchujelly.cluttered.fabric.ClutteredFabric;
import net.redchujelly.cluttered.fabric.entity.ChairEntity;

public final class ClutteredEntityRegistry {
    public static final EntityType<ChairEntity> CHAIR_ENTITY = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(ClutteredFabric.MODID, "chair_entity"),
            EntityType.Builder.of(ChairEntity::create, MobCategory.MISC)
                    .sized(1.0f, 1.0f)
                    .build(ResourceLocation.fromNamespaceAndPath(ClutteredFabric.MODID, "chair_entity").toString())
    );

    private ClutteredEntityRegistry() {
    }

    public static void register() {
        ClutteredFabric.LOGGER.info("Registering Fabric entities");
    }
}

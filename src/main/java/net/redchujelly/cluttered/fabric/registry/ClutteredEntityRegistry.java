package net.redchujelly.cluttered.fabric.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.redchujelly.cluttered.fabric.ClutteredFabric;
import net.redchujelly.cluttered.fabric.entity.ChairEntity;

import java.util.Objects;

public final class ClutteredEntityRegistry {
    private static final String CHAIR_ENTITY_PATH = "chair_entity";
    private static final ResourceLocation CHAIR_ENTITY_ID = createId(CHAIR_ENTITY_PATH);

    public static final EntityType<ChairEntity> CHAIR_ENTITY = Registry.register(
        Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE, "entity type registry"),
        Objects.requireNonNull(CHAIR_ENTITY_ID, "chair entity id"),
        Objects.requireNonNull(
            EntityType.Builder.of(ChairEntity::create, MobCategory.MISC)
                .sized(1.0f, 1.0f)
                .build(Objects.requireNonNull(CHAIR_ENTITY_ID.toString(), "chair entity id string")),
            "chair entity type"
        )
    );

    private ClutteredEntityRegistry() {
    }

    public static void register() {
        ClutteredFabric.LOGGER.info("Registering Fabric entities");
    }

    private static ResourceLocation createId(String path) {
        return Objects.requireNonNull(
                ResourceLocation.fromNamespaceAndPath(Objects.requireNonNull(ClutteredFabric.MODID, "mod id"), Objects.requireNonNull(path, "path")),
                "resource location"
        );
    }
}

package net.redchujelly.cluttered.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.redchujelly.cluttered.fabric.client.ChairEntityRenderer;

import java.util.Objects;

public final class ClutteredFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerChairRenderer();
        registerBlockEntityRenderer("cluttered_sign", SignRenderer::new);
        registerBlockEntityRenderer("cluttered_hanging_sign", HangingSignRenderer::new);
        registerSpecialRenderLayers();

        ClutteredFabric.LOGGER.info("Cluttered Fabric client bootstrap initialized");
    }

    private static void registerSpecialRenderLayers() {
        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null || !Objects.equals(id.getNamespace(), ClutteredFabric.MODID)) {
                continue;
            }

            String path = id.getPath();
            if (usesTranslucentLayer(path)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent());
                continue;
            }

            // Most decorative models in this port rely on transparent pixels.
            // Applying cutout by default avoids black quads on Fabric when no explicit
            // per-block render layer registration exists.
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
        }
    }

    private static boolean usesTranslucentLayer(String path) {
        return path.contains("translucent")
                || path.contains("window")
                || path.contains("glass");
    }

    private static void registerChairRenderer() {
        ResourceLocation id = createId("chair_entity");
        if (!Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE, "entity type registry").containsKey(Objects.requireNonNull(id, "chair entity id"))) {
            ClutteredFabric.LOGGER.warn("Skipping chair renderer registration, missing entity type: {}", id);
            return;
        }

        EntityType<?> entityType = Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.get(Objects.requireNonNull(id, "chair entity id")), "chair entity type");
        registerChairRendererUnchecked(entityType);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void registerChairRendererUnchecked(EntityType<?> entityType) {
        EntityRendererRegistry.register((EntityType) Objects.requireNonNull(entityType, "entity type"), ChairEntityRenderer::new);
    }

    private static <T extends BlockEntity> void registerBlockEntityRenderer(String path, BlockEntityRendererProvider<T> provider) {
        ResourceLocation id = createId(path);
        if (!Objects.requireNonNull(BuiltInRegistries.BLOCK_ENTITY_TYPE, "block entity type registry").containsKey(Objects.requireNonNull(id, "block entity type id"))) {
            ClutteredFabric.LOGGER.warn("Skipping block entity renderer registration, missing type: {}", id);
            return;
        }

        BlockEntityType<?> blockEntityType = Objects.requireNonNull(BuiltInRegistries.BLOCK_ENTITY_TYPE.get(Objects.requireNonNull(id, "block entity type id")), "block entity type");
        registerBlockEntityRendererUnchecked(blockEntityType, Objects.requireNonNull(provider, "renderer provider"));
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> void registerBlockEntityRendererUnchecked(
            BlockEntityType<?> blockEntityType,
            BlockEntityRendererProvider<T> provider
    ) {
        BlockEntityRenderers.register(
                (BlockEntityType<? extends T>) Objects.requireNonNull(blockEntityType, "block entity type"),
                Objects.requireNonNull(provider, "renderer provider")
        );
    }

    private static ResourceLocation createId(String path) {
        return Objects.requireNonNull(
                ResourceLocation.fromNamespaceAndPath(Objects.requireNonNull(ClutteredFabric.MODID, "mod id"), Objects.requireNonNull(path, "path")),
                "resource location"
        );
    }
}

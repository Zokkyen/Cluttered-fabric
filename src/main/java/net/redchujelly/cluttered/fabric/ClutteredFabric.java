package net.redchujelly.cluttered.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.redchujelly.cluttered.block.custom.CustomHorizontalWoodBlock;
import net.redchujelly.cluttered.block.custom.CustomLeavesBlock;
import net.redchujelly.cluttered.block.custom.CustomLogLikeBlock;
import net.redchujelly.cluttered.block.custom.CustomWoodBlock;
import net.redchujelly.cluttered.block.custom.CustomWoodFenceBlock;
import net.redchujelly.cluttered.block.custom.CustomWoodFenceGate;
import net.redchujelly.cluttered.block.custom.CustomWoodSlabBlock;
import net.redchujelly.cluttered.block.custom.CustomWoodStairs;
import net.redchujelly.cluttered.block.custom.WoodPicketFenceGateBlock;
import net.redchujelly.cluttered.fabric.config.ClutteredFabricConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClutteredFabric implements ModInitializer {
    public static final String MODID = "cluttered";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final String COMMON_BOOTSTRAP_CLASS = "net.redchujelly.cluttered.Cluttered";
    private static ClutteredFabricConfig config;

    public static ClutteredFabricConfig getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        config = ClutteredFabricConfig.load(LOGGER);
        initializeCommonBootstrap();
        registerFlammables();
        LOGGER.info("Cluttered Fabric bootstrap initialized (1.21.1)");
        LOGGER.info("Config replaceOldClutteredFurniture={}", config.replaceOldClutteredFurniture);
    }

    private static void initializeCommonBootstrap() {
        try {
            Class<?> bootstrapClass = Class.forName(COMMON_BOOTSTRAP_CLASS);
            bootstrapClass.getMethod("init").invoke(null);
        } catch (ReflectiveOperationException | LinkageError exception) {
            LOGGER.error("Failed to initialize common Cluttered bootstrap", exception);
        }
    }

    private static void registerFlammables() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null || !MODID.equals(id.getNamespace())) {
                continue;
            }

            if (block instanceof CustomLeavesBlock) {
                registry.add(block, 30, 60);
                continue;
            }

            if (block instanceof CustomLogLikeBlock) {
                registry.add(block, 5, 5);
                continue;
            }

            if (block instanceof CustomWoodBlock
                || block instanceof CustomHorizontalWoodBlock
                || block instanceof CustomWoodFenceBlock
                || block instanceof CustomWoodFenceGate
                || block instanceof CustomWoodSlabBlock
                || block instanceof CustomWoodStairs
                || block instanceof WoodPicketFenceGateBlock) {
                registry.add(block, 5, 20);
            }
        }
    }
}

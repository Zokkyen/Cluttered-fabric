package net.redchujelly.cluttered.fabric;

import net.fabricmc.api.ModInitializer;
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
}

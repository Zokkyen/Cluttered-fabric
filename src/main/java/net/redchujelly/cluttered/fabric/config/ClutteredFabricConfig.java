package net.redchujelly.cluttered.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ClutteredFabricConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("cluttered-common.json");

    public boolean replaceOldClutteredFurniture = true;

    public static ClutteredFabricConfig load(Logger logger) {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                ClutteredFabricConfig config = new ClutteredFabricConfig();
                config.save(logger);
                return config;
            }

            try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
                ClutteredFabricConfig config = GSON.fromJson(reader, ClutteredFabricConfig.class);
                if (config == null) {
                    logger.warn("Config file was empty, using defaults: {}", CONFIG_PATH);
                    config = new ClutteredFabricConfig();
                }
                return config;
            }
        } catch (IOException exception) {
            logger.error("Failed to load config {}, using defaults", CONFIG_PATH, exception);
            return new ClutteredFabricConfig();
        }
    }

    public void save(Logger logger) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException exception) {
            logger.error("Failed to save config {}", CONFIG_PATH, exception);
        }
    }
}

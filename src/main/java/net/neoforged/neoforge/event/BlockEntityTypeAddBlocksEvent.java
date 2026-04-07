package net.neoforged.neoforge.event;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class BlockEntityTypeAddBlocksEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger("cluttered-fabric-compat");
    private static Field cachedValidBlocksField;

    public <T extends BlockEntity> void modify(BlockEntityType<? extends T> blockEntityType, Block... blocks) {
        if (blockEntityType == null || blocks == null || blocks.length == 0) {
            return;
        }

        try {
            Field validBlocksField = getValidBlocksField();
            if (validBlocksField == null) {
                LOGGER.warn("Unable to patch BlockEntityType valid blocks for {}", blockEntityType);
                return;
            }

            @SuppressWarnings("unchecked")
            Set<Block> existingBlocks = (Set<Block>) validBlocksField.get(blockEntityType);
            if (existingBlocks == null) {
                return;
            }

            try {
                existingBlocks.addAll(Arrays.asList(blocks));
            } catch (UnsupportedOperationException immutableSet) {
                LinkedHashSet<Block> mergedBlocks = new LinkedHashSet<>(existingBlocks);
                mergedBlocks.addAll(Arrays.asList(blocks));
                validBlocksField.set(blockEntityType, Set.copyOf(mergedBlocks));
            }
        } catch (ReflectiveOperationException exception) {
            LOGGER.error("Failed to extend BlockEntityType valid blocks for {}", blockEntityType, exception);
        }
    }

    private static Field getValidBlocksField() {
        if (cachedValidBlocksField != null) {
            return cachedValidBlocksField;
        }

        for (Field field : BlockEntityType.class.getDeclaredFields()) {
            if (!Set.class.isAssignableFrom(field.getType())) {
                continue;
            }

            field.setAccessible(true);
            cachedValidBlocksField = field;
            return cachedValidBlocksField;
        }

        return null;
    }
}

package net.redchujelly.cluttered;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.redchujelly.cluttered.setup.BlockRegistration;
import net.redchujelly.cluttered.setup.CreativeTabRegistration;
import net.redchujelly.cluttered.setup.EntityTypeRegistration;
import net.redchujelly.cluttered.setup.FoliagePlacerTypeRegistration;
import net.redchujelly.cluttered.setup.ItemRegistration;
import net.redchujelly.cluttered.setup.SoundRegistration;
import net.redchujelly.cluttered.setup.TileEntityRegistration;
import net.redchujelly.cluttered.setup.TreeDecoratorTypeRegistration;
import net.redchujelly.cluttered.setup.TrunkPlacerTypeRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public final class Cluttered {
    public static final String MODID = "cluttered";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final IEventBus NOOP_EVENT_BUS = new IEventBus() {
    };

    private static boolean initialized;

    private Cluttered() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }

        SoundRegistration.register(NOOP_EVENT_BUS);
        EntityTypeRegistration.register(NOOP_EVENT_BUS);
        BlockRegistration.register(NOOP_EVENT_BUS);
        ItemRegistration.register(NOOP_EVENT_BUS);
        TileEntityRegistration.register(NOOP_EVENT_BUS);
        TileEntityRegistration.addBlocks(new BlockEntityTypeAddBlocksEvent());
        CreativeTabRegistration.register(NOOP_EVENT_BUS);
        TrunkPlacerTypeRegistration.register(NOOP_EVENT_BUS);
        FoliagePlacerTypeRegistration.register(NOOP_EVENT_BUS);
        TreeDecoratorTypeRegistration.register(NOOP_EVENT_BUS);

        registerCompostables();
        registerFlowerPotPlants();

        initialized = true;
        LOGGER.info("Cluttered core initialized on Fabric");
    }

    private static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.WILLOW_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.FLOWERING_CARPET_WILLOW.get().asItem(), 0.075F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.WILLOW_SAPLING.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.WILLOW_VINES.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.POPLAR_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.FLOWERING_CARPET_POPLAR.get().asItem(), 0.075F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.FLOWERING_POPLAR_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.POPLAR_SAPLING.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.CRABAPPLE_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.FLOWERING_CARPET_CRABAPPLE.get().asItem(), 0.075F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.CRABAPPLE_SAPLING.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.SYCAMORE_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.SYCAMORE_SAPLING.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.MAPLE_LEAVES_FLOWERING.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.MAPLE_LEAVES.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.MAPLE_SAPLING.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.BLUE_MUSHROOM_SAPLING.get().asItem(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(BlockRegistration.RED_MUSHROOM_SAPLING.get().asItem(), 0.65F);
    }

    private static void registerFlowerPotPlants() {
        Map<Block, Block> contentToPotted = getFlowerPotContents();
        registerFlowerPotPlant(contentToPotted, BlockRegistration.WILLOW_SAPLING, BlockRegistration.POTTED_WILLOW_SAPLING);
        registerFlowerPotPlant(contentToPotted, BlockRegistration.POPLAR_SAPLING, BlockRegistration.POTTED_POPLAR_SAPLING);
        registerFlowerPotPlant(contentToPotted, BlockRegistration.CRABAPPLE_SAPLING, BlockRegistration.POTTED_CRABAPPLE_SAPLING);
        registerFlowerPotPlant(contentToPotted, BlockRegistration.SYCAMORE_SAPLING, BlockRegistration.POTTED_SYCAMORE_SAPLING);
        registerFlowerPotPlant(contentToPotted, BlockRegistration.MAPLE_SAPLING, BlockRegistration.POTTED_MAPLE_SAPLING);
        registerFlowerPotPlant(contentToPotted, BlockRegistration.BLUE_MUSHROOM_SAPLING, BlockRegistration.POTTED_BLUE_MUSHROOM_SAPLING);
        registerFlowerPotPlant(contentToPotted, BlockRegistration.RED_MUSHROOM_SAPLING, BlockRegistration.POTTED_RED_MUSHROOM_SAPLING);
    }

    private static void registerFlowerPotPlant(
            Map<Block, Block> contentToPotted,
            DeferredHolder<Block, ? extends Block> plant,
            DeferredHolder<Block, ? extends Block> pottedPlant
    ) {
        contentToPotted.put(plant.get(), pottedPlant.get());
    }

    @SuppressWarnings("unchecked")
    private static Map<Block, Block> getFlowerPotContents() {
        try {
            Field contentsField = FlowerPotBlock.class.getDeclaredField("CONTENT_TO_POTTED");
            contentsField.setAccessible(true);
            return (Map<Block, Block>) contentsField.get(null);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to register Cluttered potted plants on Fabric", exception);
        }
    }
}

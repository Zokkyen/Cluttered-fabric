package net.redchujelly.cluttered.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.redchujelly.cluttered.Cluttered;

public class ClutteredWoodTypes {
    public static final WoodType WILLOW = register("willow", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType FLOWERING_WILLOW = register("flowering_willow", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType POPLAR = register("poplar", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType FLOWERING_POPLAR = register("flowering_poplar", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType CRABAPPLE = register("crabapple", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType FLOWERING_CRABAPPLE = register("flowering_crabapple", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType SYCAMORE = register("sycamore", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType MAPLE = register("fluorescent_maple", WoodType.OAK, BlockSetType.OAK);
    public static final WoodType RED_MUSHROOM = register("red_mushroom", WoodType.CRIMSON, BlockSetType.CRIMSON);
    public static final WoodType BLUE_MUSHROOM = register("blue_mushroom", WoodType.WARPED, BlockSetType.WARPED);

    private ClutteredWoodTypes() {
    }

    private static WoodType register(String name, WoodType baseType, BlockSetType blockSetType) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Cluttered.MODID, name);
        return WoodTypeBuilder.copyOf(baseType).register(id, blockSetType);
    }
}

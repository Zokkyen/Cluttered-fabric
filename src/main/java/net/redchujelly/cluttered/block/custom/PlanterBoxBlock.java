package net.redchujelly.cluttered.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class PlanterBoxBlock extends CustomWoodBlock{
    public PlanterBoxBlock(Properties pProperties) {
        super(pProperties);
    }
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}


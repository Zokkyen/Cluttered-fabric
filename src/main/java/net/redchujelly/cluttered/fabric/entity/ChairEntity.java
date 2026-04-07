package net.redchujelly.cluttered.fabric.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import javax.annotation.Nonnull;

import java.util.Objects;

public class ChairEntity extends Entity {
    private final BlockPos chairPos;
    private final Level chairLevel;

    public ChairEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.chairPos = BlockPos.ZERO;
        this.chairLevel = level;
    }

    public ChairEntity(EntityType<?> entityType, Level level, BlockPos pos) {
        super(entityType, level);
        this.chairPos = pos;
        this.chairLevel = level;
    }

    public static ChairEntity create(EntityType<ChairEntity> entityType, Level level) {
        return new ChairEntity(entityType, level);
    }

    @Override
    protected void defineSynchedData(@Nonnull SynchedEntityData.Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag compoundTag) {
    }

    @Override
    protected void removePassenger(@Nonnull Entity passenger) {
        this.teleportTo(this.getX(), this.getY() + 0.5f, this.getZ());
        super.removePassenger(passenger);
        kill();
    }

    @Override
    public void kill() {
        BlockPos position = Objects.requireNonNull(chairPos, "chair position");
        BooleanProperty occupiedProperty = Objects.requireNonNull(BlockStateProperties.OCCUPIED, "occupied property");
        BlockState chair = chairLevel.getBlockState(position);
        if (chair.hasProperty(occupiedProperty)) {
            this.teleportTo(this.getX(), this.getY() + 0.5f, this.getZ());
            BlockState unoccupiedChair = chair.setValue(occupiedProperty, false);
            chairLevel.setBlock(position, Objects.requireNonNull(unoccupiedChair, "unoccupied chair state"), 2);
        }
        super.kill();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}

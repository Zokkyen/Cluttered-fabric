package net.redchujelly.cluttered.fabric.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityBedCheckMixin {
    @Inject(method = "checkBedExists", at = @At("HEAD"), cancellable = true)
    private void cluttered$allowTaggedBeds(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        Optional<BlockPos> sleepingPos = self.getSleepingPos();

        if (sleepingPos.isEmpty()) {
            return;
        }

        BlockPos bedPos = Objects.requireNonNull(sleepingPos.get(), "sleeping block position");
        BlockState state = self.level().getBlockState(bedPos);
        if (state.is(Objects.requireNonNull(BlockTags.BEDS, "beds tag"))) {
            cir.setReturnValue(true);
        }
    }
}

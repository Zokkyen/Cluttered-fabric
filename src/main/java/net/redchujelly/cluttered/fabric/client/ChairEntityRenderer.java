package net.redchujelly.cluttered.fabric.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.redchujelly.cluttered.fabric.entity.ChairEntity;
import javax.annotation.Nonnull;

public class ChairEntityRenderer extends EntityRenderer<ChairEntity> {
    public ChairEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(@Nonnull ChairEntity entity) {
        return null;
    }
}

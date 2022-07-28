package com.lodestar.aileron.mixin;

import com.lodestar.aileron.ICameraEMA;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin implements ICameraEMA {

    @Shadow public abstract float getYRot();

    double previousEMAValue = 0.0;
    double EMAValue = 0.0;
    float smoothedEMADifference = 0.0f;

    @Inject(method = "setup", at = @At("TAIL"))
    public void setup(BlockGetter blockGetter, Entity entity, boolean bl, boolean bl2, float partial, CallbackInfo ci) {
        smoothedEMADifference = getYRot() - (float) (EMAValue + (EMAValue - previousEMAValue) * partial);
    }

    @Override
    public double getPreviousEMAValue() {
        return previousEMAValue;
    }

    @Override
    public double getEMAValue() {
        return EMAValue;
    }

    @Override
    public void setPreviousEMAValue(float previousEMA) {
        previousEMAValue = previousEMA;
    }

    @Override
    public void setEMAValue(float EMA) {
        EMAValue = EMA;
    }

    @Override
    public float getSmoothedEMADifference() {
        return smoothedEMADifference;
    }
}
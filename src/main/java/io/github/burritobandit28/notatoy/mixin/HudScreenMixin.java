package io.github.burritobandit28.notatoy.mixin;

import io.github.burritobandit28.notatoy.NotAClient;
import io.github.burritobandit28.notatoy.NotAToy;
import io.github.burritobandit28.notatoy.effects.RadiationSicknessEffect;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class HudScreenMixin {



    @Shadow
    @Nullable
    protected abstract PlayerEntity getCameraPlayer();

    @Inject(method = "drawHeart", at = @At("TAIL"), cancellable = true)
    private void drawRadioHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        if (this.getCameraPlayer().hasStatusEffect(RadiationSicknessEffect.RADIATION_SICKNESS) && !type.equals(InGameHud.HeartType.CONTAINER)) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, getTexture(half, blinking), x, y, 9, 9);
            //ci.cancel();
        }

    }

    private Identifier getTexture(boolean half, boolean blinking) {

        if (half) {
            return blinking ? NotAClient.halfBlinkingTexture : NotAClient.halfTexture;
        } else {
            return blinking ? NotAClient.fullBlinkingTexture : NotAClient.fullTexture;
        }

    }


}

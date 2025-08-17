package io.github.burritobandit28.notatoy;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

public class NotAClient implements ClientModInitializer {


    public static Identifier halfBlinkingTexture;
    public static Identifier fullBlinkingTexture;
    public static Identifier fullTexture;
    public static Identifier halfTexture;

    @Override
    public void onInitializeClient() {
        halfBlinkingTexture = NotAToy.ID("hud/heart/half_blinking");
        fullBlinkingTexture = NotAToy.ID("hud/heart/full_blinking");
        fullTexture = NotAToy.ID("hud/heart/full");
        halfTexture = NotAToy.ID("hud/heart/half");
    }
}

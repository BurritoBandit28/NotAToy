package io.github.burritobandit28.notatoy.mixin;

import io.github.burritobandit28.notatoy.NotAToy;
import io.github.burritobandit28.notatoy.sounds.ModSounds;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {

    @Inject(method = "getSound", at = @At("HEAD"), cancellable = true)
    private void divineIntervention(CallbackInfoReturnable<RegistryEntry<SoundEvent>> cir) {
        if (Objects.equals(((NoteBlockInstrument) (Object) this).name(), "HEV")) {
            cir.setReturnValue(ModSounds.HEV_RAD_SOUND);
        }
    }
}

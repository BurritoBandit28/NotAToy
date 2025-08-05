package io.github.burritobandit28.notatoy.sounds;

import io.github.burritobandit28.notatoy.NotAToy;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    private ModSounds() {
        // woooo
    }

    // 12 gives original sound
    public static RegistryEntry.Reference<SoundEvent> HEV_RAD_SOUND = registerReference(Identifier.of(NotAToy.MOD_ID,"block.note_block.hev"));;

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void init() {
        NotAToy.LOGGER.info("registering the sound");
    }

}

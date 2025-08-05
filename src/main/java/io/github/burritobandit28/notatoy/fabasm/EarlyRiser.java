package io.github.burritobandit28.notatoy.fabasm;

import com.chocohead.mm.api.ClassTinkerers;
import io.github.burritobandit28.notatoy.NotAToy;
import io.github.burritobandit28.notatoy.sounds.ModSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

// is early riser meant to be a dick joke?
public class EarlyRiser implements Runnable {


    // a bunch of this yoinked from https://github.com/Chocohead/Fabric-ASM/blob/master/example/src/com/chocohead/mm/testing/EarlyRiser.java
    @Override
    public void run() {

        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        // add to noteblock enum
        NotAToy.LOGGER.info("Hey from early riser lol");

        String noteBlockInstrument = remapper.mapClassName("intermediary", "net.minecraft.class_2766");
        String registryEntry = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_6880") + ";";
        String noteBlockInstrumentType = "L" + remapper.mapClassName("intermediary", "net.minecraft.class_2766$class_7994") + ";";
        ClassTinkerers.enumBuilder(noteBlockInstrument, String.class, registryEntry, noteBlockInstrumentType)
                .addEnum("HEV", "hev", null, NoteBlockInstrument.Type.BASE_BLOCK).build();

    }


}

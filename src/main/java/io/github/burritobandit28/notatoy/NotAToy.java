package io.github.burritobandit28.notatoy;

import io.github.burritobandit28.notatoy.blocks.BlockRegister;
import io.github.burritobandit28.notatoy.effects.RadiationSicknessEffect;
import io.github.burritobandit28.notatoy.items.ItemRegister;
import io.github.burritobandit28.notatoy.items.ModItemGroup;
import io.github.burritobandit28.notatoy.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotAToy implements ModInitializer {
	public static final String MOD_ID = "notatoy";

    public static Identifier ID(String path) {
        return Identifier.of(MOD_ID, path);
    }

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // tags
    public static final TagKey<EntityType<?>> GOOP_IGNORED = TagKey.of(RegistryKeys.ENTITY_TYPE, ID("goop_ignored"));
    public static final TagKey<EntityType<?>> RADIATION_IGNORED = TagKey.of(RegistryKeys.ENTITY_TYPE, ID("radiation_ignored"));



	@Override
	public void onInitialize() {
		ModSounds.init();
		ItemRegister.init();
		BlockRegister.init();
        ModItemGroup.init();
        RadiationSicknessEffect.reg();
        TemporaryShowcaseCommands.regCommands();
	}
}
package io.github.burritobandit28.notatoy;

import io.github.burritobandit28.notatoy.blocks.BlockRegister;
import io.github.burritobandit28.notatoy.items.ItemRegister;
import io.github.burritobandit28.notatoy.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.BlockItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotAToy implements ModInitializer {
	public static final String MOD_ID = "notatoy";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModSounds.init();
		ItemRegister.init();
		BlockRegister.init();
	}
}
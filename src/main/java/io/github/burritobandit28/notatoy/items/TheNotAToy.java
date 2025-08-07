package io.github.burritobandit28.notatoy.items;

import io.github.burritobandit28.notatoy.sounds.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TheNotAToy extends Item {
    public TheNotAToy(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return super.use(world,user,hand);
    }



}

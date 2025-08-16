package io.github.burritobandit28.notatoy.blocks;

import io.github.burritobandit28.notatoy.items.ItemRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WasteBlock extends RadioactiveBlock{
    public WasteBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        player.giveItemStack(ItemRegister.RADIOACTIVE_FUN_GOOP.getDefaultStack());

        return ActionResult.SUCCESS;
    }

}

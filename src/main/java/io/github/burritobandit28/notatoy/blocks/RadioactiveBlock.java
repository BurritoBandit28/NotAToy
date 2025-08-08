package io.github.burritobandit28.notatoy.blocks;

import io.github.burritobandit28.notatoy.accessors.isSourcePresentAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class RadioactiveBlock extends Block {
    public RadioactiveBlock(Settings settings) {
        super(settings);
    }



    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        Box area = new Box(pos).stretch(10.0,10.0,10.0);
        List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, area);
        for(LivingEntity entity : entities ) {
            ((isSourcePresentAccessor)entity).addBlockSource(pos);
        }

    }


}

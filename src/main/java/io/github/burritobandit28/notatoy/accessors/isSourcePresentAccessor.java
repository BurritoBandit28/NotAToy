package io.github.burritobandit28.notatoy.accessors;

import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.UUID;

public interface isSourcePresentAccessor {

    boolean isSourcePresent();

    List<Pair<BlockPos, Integer>> getBlockSources();

    List<Pair<UUID, Integer>> getEntitySources();

    void addBlockSource(BlockPos sourcePos);

    void addEntitySource(UUID entityUUID);

    boolean hasEntitySource(UUID entityUUID);

}

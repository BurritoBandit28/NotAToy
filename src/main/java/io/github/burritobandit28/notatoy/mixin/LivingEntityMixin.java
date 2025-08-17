package io.github.burritobandit28.notatoy.mixin;

import io.github.burritobandit28.notatoy.NotAToy;
import io.github.burritobandit28.notatoy.TemporaryShowcaseCommands;
import io.github.burritobandit28.notatoy.accessors.EnableDisableRadiation;
import io.github.burritobandit28.notatoy.accessors.isSourcePresentAccessor;
import io.github.burritobandit28.notatoy.blocks.RadioactiveBlock;
import io.github.burritobandit28.notatoy.effects.RadiationSicknessEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements isSourcePresentAccessor, EnableDisableRadiation {


    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean isInCreativeMode();

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    private List<Pair<BlockPos,Integer>> blockSources = new ArrayList<>();
    private List<Pair<UUID,Integer>> entitySources = new ArrayList<>(); // I never finished adding this

    private boolean doRadiation = false;


    @Override
    public void set_value(boolean val) {
        this.doRadiation = val;
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    private void doRadiation(CallbackInfo ci) {

        World world = this.getWorld();

        if (this.hasStatusEffect(RadiationSicknessEffect.RADIATION_SICKNESS) && !world.isClient) {
            BlockPos pos = this.getBlockPos();
            Box area = new Box(pos).stretch(10.0,10.0,10.0);
            List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class, area);
            for(LivingEntity entity : entities ) {
                if (!((isSourcePresentAccessor)entity).hasEntitySource(this.getUuid())) {
                    ((isSourcePresentAccessor)entity).addEntitySource(this.getUuid());
                }
            }

        }

        if (TemporaryShowcaseCommands.radiation_opt_in.getOrDefault(this.getUuid(), false) || (!this.isPlayer()&&!this.getType().isIn(NotAToy.RADIATION_IGNORED))) {
            this.tickSources();


            if (this.isSourcePresent() && !world.isClient) {
                int sources = this.blockSources.size() + (this.entitySources.size());
                if (sources > 7 || this.entitySources.size() > 2) {
                    this.addStatusEffect(new StatusEffectInstance(RadiationSicknessEffect.RADIATION_SICKNESS, 18005));
                    if (!this.hasEntitySource(this.getUuid())) {
                        this.addEntitySource(this.getUuid());
                    }
                }

                double value = (-Math.exp(-(sources / 1500.0)) + 1);

                if (Math.random() < value) {
                    this.damage((ServerWorld) world, RadiationSicknessEffect.getRadiationDamageSource((ServerWorld) world), 1);
                }
            }
        }
    }

    @Unique
    private void tickSources() {

        List<Pair<BlockPos,Integer>> blockToRemove = new ArrayList<>();
        List<Pair<UUID,Integer>> entityToRemove = new ArrayList<>();

        if (!this.getWorld().isClient) {
            for (Pair<BlockPos, Integer> blockSource : this.blockSources) {
                if (blockSource.getRight() > 600) {
                    if ((!blockSource.getLeft().isWithinDistance(this.getPos(), 60.0))||!(this.getWorld().getBlockState(blockSource.getLeft()).getBlock() instanceof RadioactiveBlock)) {
                        blockToRemove.add(blockSource);
                    }
                    else {
                        blockSource.setRight(0);
                    }
                } else {
                    Integer last = blockSource.getRight();
                    blockSource.setRight(last + 1);
                }
            }
            for (Pair<UUID, Integer> entitySource : this.entitySources) {
                if (entitySource.getRight() > 600) {
                    LivingEntity entity = (LivingEntity) this.getWorld().getEntity(entitySource.getLeft());

                    if (entity==null||!entity.getBlockPos().isWithinDistance(this.getPos(), 60.0) || !entity.hasStatusEffect(RadiationSicknessEffect.RADIATION_SICKNESS) || !entity.isAlive()) {
                        entityToRemove.add(entitySource);
                    }
                    else {
                        entitySource.setRight(0);
                    }
                } else {
                    Integer last = entitySource.getRight();
                    entitySource.setRight(last + 1);
                }
            }

            // not proud of this tbh but hey it works and its like only ever gonna be a list of one
            for (Pair<BlockPos,Integer> entry : blockToRemove) {
                this.blockSources.remove(entry);
            }
            for (Pair<UUID,Integer> entry : entityToRemove) {
                this.entitySources.remove(entry);
            }

        }
    }

    @Override
    public boolean isSourcePresent() {
        // set based of own inventory or other players'
        // blocks too
        return !(this.blockSources.isEmpty()&&this.entitySources.isEmpty());
    }

    @Override
    public List<Pair<BlockPos, Integer>> getBlockSources() {
        return this.blockSources;
    }

    @Override
    public List<Pair<UUID, Integer>> getEntitySources() {
        return this.entitySources;
    }

    @Override
    public void addBlockSource(BlockPos sourcePos) {
        if (!(this.blockSources.contains(sourcePos))) {
            this.blockSources.add(new Pair<>(sourcePos, 0));
        }
    }

    @Override
    public void addEntitySource(UUID entityUUID) {
        if (!(this.entitySources.contains(entityUUID))) {
            this.entitySources.add(new Pair<>(entityUUID, 0));
        }
    }

    @Override
    public boolean hasEntitySource(UUID entityUUID) {
        for (Pair<UUID, Integer> entry : this.entitySources) {
            if (entry.getLeft()==entityUUID) {
                return true;
            }
        }
        return false;
    };

}

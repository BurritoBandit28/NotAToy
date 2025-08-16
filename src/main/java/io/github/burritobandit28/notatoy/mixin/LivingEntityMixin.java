package io.github.burritobandit28.notatoy.mixin;

import io.github.burritobandit28.notatoy.accessors.EnableDisableRadiation;
import io.github.burritobandit28.notatoy.accessors.isSourcePresentAccessor;
import io.github.burritobandit28.notatoy.blocks.RadioactiveBlock;
import io.github.burritobandit28.notatoy.effects.RadiationSicknessEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
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

    private List<Pair<BlockPos,Integer>> blockSources = new ArrayList<>();
    private List<Pair<UUID,Integer>> entitySources = new ArrayList<>(); // I never finished adding this

    private boolean doRadiation = false;


    @Override
    public void set_value(boolean val) {
        this.doRadiation = val;
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    private void doRadiation(CallbackInfo ci) {

        if (!this.isInCreativeMode() || this.doRadiation ) {
            this.tickSources();


            //System.out.println(this.isSourcePresent());
            if (this.isSourcePresent() && !this.getWorld().isClient) {
                // 1 in say 200 chance deal damage to entity
                int sources = this.blockSources.size() + this.entitySources.size();
                if (sources > 7) {
                    this.addStatusEffect(new StatusEffectInstance(RadiationSicknessEffect.RADIATION_SICKNESS, 18005));
                }

                double value = (-Math.exp(-(sources / 1500.0)) + 1);

                if (Math.random() < value) {
                    this.damage((ServerWorld) this.getWorld(), RadiationSicknessEffect.getRadiationDamageSource((ServerWorld) this.getWorld()), 1);
                }


                //TODO - remember to clear sources and recalculate every so often - also clear when changing dimension
            }
        }
    }

    @Unique
    private void tickSources() {
        if (!this.getWorld().isClient) {
            for (Pair<BlockPos, Integer> blockSource : this.blockSources) {
                if (blockSource.getRight() > 1200) {
                    if ((!blockSource.getLeft().isWithinDistance(this.getPos(), 100.0))&&this.getWorld().getBlockState(blockSource.getLeft()).getBlock() instanceof RadioactiveBlock) {
                        this.blockSources.remove(blockSource);
                    }
                } else {
                    Integer last = blockSource.getRight();
                    blockSource.setRight(last + 1);
                }
            }
            for (Pair<UUID, Integer> entitySource : this.entitySources) {
                if (entitySource.getRight() > 1200) {
                    Entity entity = this.getWorld().getEntity(entitySource.getLeft());
                    if (!entity.getBlockPos().isWithinDistance(this.getPos(), 100.0)) {
                        this.blockSources.remove(entitySource);
                    }
                } else {
                    Integer last = entitySource.getRight();
                    entitySource.setRight(last + 1);
                }
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
}

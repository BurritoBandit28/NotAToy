package io.github.burritobandit28.notatoy.mixin;

import io.github.burritobandit28.notatoy.items.ItemRegister;
import io.github.burritobandit28.notatoy.items.RadioactiveFunGoop;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConsumableComponent.class)
public class ConsumableComponentMixin {


    @Inject(at = @At("HEAD"), method = "finishConsumption")
    private void blowUpIdiot(World world, LivingEntity user, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {

        System.out.println(stack);
        if (stack.getItem() == ItemRegister.RADIOACTIVE_FUN_GOOP   && !world.isClient ) {

            if (!user.isInCreativeMode()) {
                user.damage((ServerWorld) world, RadioactiveFunGoop.getGoopDamageSource((ServerWorld) world), Float.MAX_VALUE);
            }

            world.createExplosion(user, Explosion.createDamageSource(world, user), GOOP_BEHAVIOUR, user.getPos(), 10.0F, false, World.ExplosionSourceType.MOB);

        }
    }


    @Unique
    private final ExplosionBehavior GOOP_BEHAVIOUR = new ExplosionBehavior() {

        @Override
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            return false;
        }

        @Override
        public boolean shouldDamage(Explosion explosion, Entity entity) {

            if (entity instanceof ItemEntity) {
                return false;
            }
            else {

                return true;
            }
        }


    };


}

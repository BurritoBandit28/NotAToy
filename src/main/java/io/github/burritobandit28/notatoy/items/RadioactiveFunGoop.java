package io.github.burritobandit28.notatoy.items;

import io.github.burritobandit28.notatoy.NotAToy;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RadioactiveFunGoop extends Item {
    public RadioactiveFunGoop(Settings settings) {
        super(settings);
    }




    public static final RegistryKey<DamageType> ATE_GOOP = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, NotAToy.ID("ate_goop"));


    public static DamageSource getGoopDamageSource(ServerWorld world) {

        return new DamageSource(world.getRegistryManager().getEntryOrThrow(ATE_GOOP));

    }







}

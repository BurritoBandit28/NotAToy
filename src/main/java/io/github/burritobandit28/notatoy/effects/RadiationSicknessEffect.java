package io.github.burritobandit28.notatoy.effects;

import io.github.burritobandit28.notatoy.NotAToy;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RadiationSicknessEffect extends StatusEffect  {

    public static final RegistryEntry<StatusEffect> RADIATION_SICKNESS =  Registry.registerReference(Registries.STATUS_EFFECT, NotAToy.ID("radiation_sickness"), new RadiationSicknessEffect());


    protected RadiationSicknessEffect() {
        super(StatusEffectCategory.HARMFUL, 0x63c74d);
    }

    public static final RegistryKey<DamageType> RADIATION_DAMAGE_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, NotAToy.ID("radiation"));


    public static DamageSource getRadiationDamageSource(ServerWorld world) {

        return new DamageSource(world.getRegistryManager().getEntryOrThrow(RADIATION_DAMAGE_KEY));

    }

    public static void reg() {

    }

}

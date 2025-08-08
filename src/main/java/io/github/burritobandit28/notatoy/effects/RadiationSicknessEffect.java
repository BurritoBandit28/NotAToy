package io.github.burritobandit28.notatoy.effects;

import io.github.burritobandit28.notatoy.NotAToy;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class RadiationSicknessEffect extends StatusEffect  {

    public static final StatusEffect RADIATION_SICKNESS = new RadiationSicknessEffect();


    protected RadiationSicknessEffect() {
        super(StatusEffectCategory.HARMFUL, 0);
    }

    public static void reg() {
        Registry.register(Registries.STATUS_EFFECT, NotAToy.ID("radiation_sickness"), RADIATION_SICKNESS);
    }

}

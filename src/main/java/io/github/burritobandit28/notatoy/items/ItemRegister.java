package io.github.burritobandit28.notatoy.items;

import io.github.burritobandit28.notatoy.NotAToy;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

public class ItemRegister {


    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(NotAToy.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static final Item RADIOACTIVE_FUN_GOOP = register("radioactive_fun_goop", RadioactiveFunGoop::new, new Item.Settings()
            .food(new FoodComponent.Builder().alwaysEdible().nutrition(0).saturationModifier(0).build(), ConsumableComponent.builder().sound(SoundEvents.ITEM_HONEY_BOTTLE_DRINK).consumeSeconds(0.75F).build()));

    public static void init(){};





}

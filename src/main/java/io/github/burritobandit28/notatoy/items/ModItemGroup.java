package io.github.burritobandit28.notatoy.items;

import io.github.burritobandit28.notatoy.NotAToy;
import io.github.burritobandit28.notatoy.blocks.BlockRegister;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class ModItemGroup {


    public static final RegistryKey<ItemGroup> NOT_A_TOY_ITEMGROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), NotAToy.ID("item_group"));
    public static final ItemGroup  NOT_A_TOY_ITEMGROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemRegister.RADIOACTIVE_FUN_GOOP))
            .displayName(Text.translatable("itemGroup.notatoy"))
            .build();

    public static void init() {

        Registry.register(Registries.ITEM_GROUP, NOT_A_TOY_ITEMGROUP_KEY, NOT_A_TOY_ITEMGROUP);

        ItemGroupEvents.modifyEntriesEvent(NOT_A_TOY_ITEMGROUP_KEY).register(itemGroup -> {
            itemGroup.add(ItemRegister.RADIOACTIVE_FUN_GOOP);
            itemGroup.add(BlockRegister.WASTEBLOCK);

        });

    }

}

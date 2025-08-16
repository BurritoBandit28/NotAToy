package io.github.burritobandit28.notatoy;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.burritobandit28.notatoy.accessors.EnableDisableRadiation;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TemporaryShowcaseCommands {



    public static void regCommands() {

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {

            dispatcher.register(literal("set_radiation")
                    .then(argument("value", BoolArgumentType.bool())
                            .executes((ctx)->{
                                Entity entity = ctx.getSource().getEntity();
                                if (entity instanceof LivingEntity livingEntity) {

                                    Boolean val = BoolArgumentType.getBool(ctx, "value");

                                    ((EnableDisableRadiation)livingEntity).set_value(val);

                                    ctx.getSource().sendFeedback(() -> Text.literal(String.format("Set radiation opt in to %s", val)), false);

                                }
                                else {
                                    ctx.getSource().sendError(Text.literal("Not a living entity!"));
                                    return 0;
                                }

                                return 1;
                            })));



        }));

    }


}

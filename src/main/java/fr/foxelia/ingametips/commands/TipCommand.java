package fr.foxelia.ingametips.commands;

import com.mojang.brigadier.CommandDispatcher;
import fr.foxelia.ingametips.datapack.TipRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public class TipCommand {

    /*
     * /displaytip <tip> [player]
     * Admin command to display a tip to one or all players
     */

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tip")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("send")
                        .then(Commands.argument("tip", ResourceLocationArgument.id())
                                .suggests((context, builder) -> {
                                    Collection<ResourceLocation> tips = TipRegistry.getAllTips().keySet();
                                    return net.minecraft.commands.SharedSuggestionProvider.suggest(tips.stream().map(ResourceLocation::toString).toList(), builder);
                                })
                                .then(Commands.argument("players", EntityArgument.players())
                                        .executes(SendTipCommand::execute)))));
    }
}

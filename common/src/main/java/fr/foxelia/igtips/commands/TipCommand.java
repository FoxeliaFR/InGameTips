package fr.foxelia.igtips.commands;

import com.mojang.brigadier.CommandDispatcher;
import fr.foxelia.igtips.datapack.TipRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.Collection;

public class TipCommand {

    /*
     * /displaytip <tip> [player]
     * Admin command to display a tip to one or all players
     */

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tip")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("send")
                        .then(CommandManager.argument("tip", IdentifierArgumentType.identifier())
                                .suggests((context, builder) -> {
                                    Collection<Identifier> tips = TipRegistry.getAllTips().keySet();
                                    return CommandSource.suggestMatching(tips.stream().map(Identifier::toString).toList(), builder);
                                })
                                .then(CommandManager.argument("players", EntityArgumentType.players())
                                        .executes(SendTipCommand::execute)))));
    }
}

package fr.foxelia.igtips.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.foxelia.igtips.datapack.TipRegistry;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collection;

public class TipCommand {

    /*
     * /tip send <tip> [player]
     * /tip random <players> <mode>      <target>           <save>
     *                       dumb/smart  global/individual  true/false
     * /tip random [player] dumb        : Random tip for all players
     *                      individual  : Random tip for each player
     *                      configlike  :
     *                      all         :
     *                                  true/false : Save the tip in the history of the player
     *
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
                                        .executes(SendTipCommand::execute))))
                .then(CommandManager.literal("random")
                        .then(CommandManager.argument("players", EntityArgumentType.players())
                                .then(CommandManager.argument("mode", StringArgumentType.word())
                                        .suggests((context, builder) -> CommandSource.suggestMatching(
                                                Arrays.stream(RandomTipCommand.RandomMode.values()).map(RandomTipCommand.RandomMode::getName),
                                                builder
                                        )).then(CommandManager.argument("save", StringArgumentType.word())
                                                .suggests((context, builder) -> CommandSource.suggestMatching(
                                                        Arrays.asList("true", "false"),
                                                        builder
                                                )).executes(RandomTipCommand::execute)
                                        ).executes(RandomTipCommand::execute)
                                )
                        )
                )

        );
    }

    public static Collection<ServerPlayerEntity> getPlayersOrError(CommandContext<ServerCommandSource> ctx) {
        Collection<ServerPlayerEntity> players;

        try {
            players = EntityArgumentType.getPlayers(ctx, "players");
        } catch (CommandSyntaxException e) {
            ServerCommandSource source = ctx.getSource();


            // Get the translation key of the error message
            if(e.getRawMessage() instanceof MutableText mt) {
                if(mt.getContent() != null && mt.getContent() instanceof TranslatableTextContent ttc) {
                    source.sendError(Text.translatable(ttc.getKey()));
                    return null;
                }
            }

            // Fallback to the raw message if the translation key is not found
            source.sendError(Text.of(e.getRawMessage().getString()));
            return null;
        }
        return players;
    }

}

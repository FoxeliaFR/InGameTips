package fr.foxelia.igtips.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.foxelia.igtips.datapack.TipRegistry;
import fr.foxelia.igtips.network.NetworkHandler;
import fr.foxelia.igtips.network.TipPacket;
import fr.foxelia.igtips.platform.PlayerLanguageHelper;
import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;

import java.util.Collection;

public class SendTipCommand {

    protected static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Identifier tipKey = IdentifierArgumentType.getIdentifier(context, "tip");
        TranslatableTip tip = TipRegistry.getTip(tipKey);
        if(tip == null) {
            source.sendError(Text.translatable("commands.igtips.tip.unknown", tipKey));
            return 0;
        }

        Collection<ServerPlayerEntity> players;

        try {
            players = EntityArgumentType.getPlayers(context, "players");
        } catch (CommandSyntaxException e) {

            // Get the translation key of the error message
            if(e.getRawMessage() instanceof MutableText mt) {
                if(mt.getContent() != null && mt.getContent() instanceof TranslatableTextContent ttc) {
                    source.sendError(Text.translatable(ttc.getKey()));
                    return 0;
                }
            }

            // Fallback to the raw message if the translation key is not found
            source.sendError(Text.of(e.getRawMessage().getString()));
            return 0;
        }

        players.forEach(player -> NetworkHandler.CHANNEL.sendToPlayer(player, new TipPacket(tip.toBasicTip(PlayerLanguageHelper.getPlayerLanguage(player)))));

        Collection<String> playerNames = players.stream().map(player -> player.getDisplayName().getString()).toList();
        source.sendFeedback(() -> Text.translatable("commands.igtips.tip.send.success", tipKey, playerNames.toString()), true);
        return 1;
    }

}

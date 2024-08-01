package fr.foxelia.ingametips.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.foxelia.ingametips.tip.TranslatableTip;
import fr.foxelia.ingametips.datapack.TipRegistry;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

import java.util.Collection;

public class SendTipCommand {

    protected static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ResourceLocation tipKey = ResourceLocationArgument.getId(context, "tip");
        TranslatableTip tip = TipRegistry.getTip(tipKey);
        if(tip == null) {
            source.sendFailure(Component.translatable("commands.igtips.tip.unknown", tipKey));
            return 0;
        }

        Collection<ServerPlayer> players;

        try {
            players = EntityArgument.getPlayers(context, "players");
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }

        players.forEach(player -> InGameTipsPacketHandler.CHANNEL.sendTo(new TipPacket(tip.toBasicTip(player.getLanguage())), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT));

        Collection<String> playerNames = players.stream().map(player -> player.getDisplayName().getString()).toList();
        source.sendSuccess(() -> Component.translatable("commands.igtips.tip.send.success", tipKey, playerNames.toString()), true);
        return 1;
    }

}

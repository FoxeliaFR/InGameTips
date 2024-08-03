package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class PlayerSchedule extends Schedule {

    private final ServerPlayer player;

    public PlayerSchedule(ServerPlayer player) {
        super(player.getStringUUID());
        this.player = player;
    }

    @Override
    protected void execute() {
        // Get a random tip
        TranslatableTip tip = getTipAndUse();
        if(tip == null) return;

        // Send the tip to the player
        InGameTipsPacketHandler.CHANNEL.sendTo(new TipPacket(tip.toBasicTip(player.getLanguage())), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);

    }
}

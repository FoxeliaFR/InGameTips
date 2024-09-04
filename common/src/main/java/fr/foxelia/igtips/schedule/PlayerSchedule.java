package fr.foxelia.igtips.schedule;

import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerSchedule extends Schedule {

    private final ServerPlayerEntity player;

    public PlayerSchedule(ServerPlayerEntity player) {
        super(player.getUuidAsString());
        this.player = player;
    }

    @Override
    protected void execute() {
        // Get a random tip
        TranslatableTip tip = getTipAndUse();
        if(tip == null) return;

        // Send the tip to the player
        sendTip(player, tip);
    }
}

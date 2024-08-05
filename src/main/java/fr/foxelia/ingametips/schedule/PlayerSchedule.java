package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.server.level.ServerPlayer;

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
        sendTip(player, tip);
    }
}

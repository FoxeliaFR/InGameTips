package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.InGameTips;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class CommonSchedule extends Schedule {

    public CommonSchedule() {
        super(ScheduleManager.COMMON_SCHEDULE);
    }

    @Override
    protected void execute() {
        if(InGameTips.SERVER.getPlayerCount() == 0) return;
        TranslatableTip tip = getTipAndUse();
        if(tip == null) return;

        // Send the tip to all players
        InGameTips.SERVER.getPlayerList().getPlayers().forEach(player -> sendTip(player, tip));
    }
}

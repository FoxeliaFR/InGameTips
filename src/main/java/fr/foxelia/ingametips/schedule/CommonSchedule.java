package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.InGameTips;
import fr.foxelia.ingametips.tip.TranslatableTip;

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

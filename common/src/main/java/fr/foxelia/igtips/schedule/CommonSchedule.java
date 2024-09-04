package fr.foxelia.igtips.schedule;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.tip.TranslatableTip;

public class CommonSchedule extends Schedule {

    public CommonSchedule() {
        super(ScheduleManager.COMMON_SCHEDULE);
    }

    @Override
    protected void execute() {
        if(InGameTips.SERVER.getCurrentPlayerCount() == 0) return;
        TranslatableTip tip = getTipAndUse();
        if(tip == null) return;

        // Send the tip to all players
        InGameTips.SERVER.getPlayerManager().getPlayerList().forEach(player -> sendTip(player, tip));
    }
}

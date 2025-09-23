package fr.foxelia.igtips.schedule;

import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.data.HistoryManager;
import fr.foxelia.igtips.data.TipHistory;
import fr.foxelia.igtips.datapack.TipRegistry;
import fr.foxelia.igtips.network.NetworkHandler;
import fr.foxelia.igtips.network.TipPacket;
import fr.foxelia.igtips.platform.PlayerLanguageHelper;
import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class Schedule {

    private final TipHistory tipHistory;

    private int currentTick = 0;
    private int executionTick = -1;

    public Schedule(String viewerID) {
        this.tipHistory = HistoryManager.getHistoryOf(viewerID);
        updateExecutionTick();
    }

    @Nullable
    protected TranslatableTip getTipAndUse() {
        Identifier tipId = tipHistory.getRandomTip(true);
        if(tipId == null) return null;
        return TipRegistry.getTip(tipId);
    }

    protected void sendTip(ServerPlayerEntity player, TranslatableTip tip) {
        NetworkHandler.CHANNEL.sendToPlayer(player, new TipPacket(tip.toBasicTip(PlayerLanguageHelper.getPlayerLanguage(player))));
    }

    public void run() {
        currentTick++;
        if(shouldExecute()) {
            execute();
            currentTick = 0;
        }
    }

    public boolean shouldExecute() {
        return currentTick >= executionTick;
    }

    public void updateExecutionTick() {
        executionTick = CommonConfig.getScheduleInterval() * 20;
    }

    protected abstract void execute();

    public void syncWithSchedule(Schedule schedule) {
        currentTick = schedule.currentTick;
        executionTick = schedule.executionTick;
    }

}

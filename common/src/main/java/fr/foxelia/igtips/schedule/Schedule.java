package fr.foxelia.igtips.schedule;

import fr.foxelia.igtips.config.InGameTipsCommonConfigs;
import fr.foxelia.igtips.data.TipHistory;
import fr.foxelia.igtips.datapack.TipRegistry;
import fr.foxelia.igtips.platform.PlayerLanguageHelper;
import fr.foxelia.igtips.network.NetworkHandler;
import fr.foxelia.igtips.network.TipPacket;
import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Schedule {

    private final TipHistory tipHistory;

    private int currentTick = 0;
    private int executionTick = -1;

    public Schedule(String viewerID) {
        this.tipHistory = new TipHistory(viewerID);
        updateExecutionTick();
    }

    protected TipHistory getTipHistory() {
        return tipHistory;
    }

    @Nullable
    protected TranslatableTip getTipAndUse() {
        Identifier tipId = getRandTip();
        if(tipId == null) return null;
        useTip(tipId);
        return TipRegistry.getTip(tipId);
    }

    @Nullable
    private Identifier getRandTip() {
        // Get all available tips
        Set<Identifier> availableTips = new HashSet<>(TipRegistry.getAllTips().keySet());
        availableTips.removeAll(getTipHistory().getViewedTips());
        if(availableTips.isEmpty()) {
            if(InGameTipsCommonConfigs.tipRecyling.get()) {
                if(!TipRegistry.getAllTips().keySet().isEmpty()) {
                    getTipHistory().getViewedTips().clear();
                    return getRandTip();
                }
            }
            return null;
        }

        // Select a random tip
        return (Identifier) availableTips.toArray()[new Random().nextInt(availableTips.size())];
    }

    private void useTip(Identifier tipId) {
        getTipHistory().addTip(tipId);
        getTipHistory().saveHistory();
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
        executionTick = InGameTipsCommonConfigs.scheduleTime.get() * 20;
    }

    protected abstract void execute();

    public void syncWithSchedule(Schedule schedule) {
        currentTick = schedule.currentTick;
        executionTick = schedule.executionTick;
    }

}

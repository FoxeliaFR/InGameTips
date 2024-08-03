package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.config.InGameTipsCommonConfigs;
import fr.foxelia.ingametips.data.TipHistory;
import fr.foxelia.ingametips.datapack.TipRegistry;
import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
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
        ResourceLocation tipId = getRandTip();
        if(tipId == null) return null;
        useTip(tipId);
        return TipRegistry.getTip(tipId);
    }

    @Nullable
    private ResourceLocation getRandTip() {
        // Get all available tips
        Set<ResourceLocation> availableTips = new HashSet<>(TipRegistry.getAllTips().keySet());
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
        return (ResourceLocation) availableTips.toArray()[new Random().nextInt(availableTips.size())];
    }

    private void useTip(ResourceLocation tipId) {
        getTipHistory().addTip(tipId);
        getTipHistory().saveHistory();
    }

    protected void sendTip(ServerPlayer player, TranslatableTip tip) {
        InGameTipsPacketHandler.CHANNEL.sendTo(new TipPacket(tip.toBasicTip(player.getLanguage())), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
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

package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.config.InGameTipsCommonConfigs;
import fr.foxelia.ingametips.data.TipHistory;
import fr.foxelia.ingametips.datapack.TipRegistry;
import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

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
        Set<ResourceLocation> availableTips = TipRegistry.getAllTips().keySet();
        availableTips.removeAll(getTipHistory().getViewedTips());
        if(availableTips.isEmpty()) return null;

        // Select a random tip
        return (ResourceLocation) availableTips.toArray()[new Random().nextInt(availableTips.size())];
    }

    private void useTip(ResourceLocation tipId) {
        getTipHistory().addTip(tipId);
        getTipHistory().saveHistory();
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

}

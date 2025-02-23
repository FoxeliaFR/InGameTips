package fr.foxelia.igtips.config.forge;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import fr.foxelia.igtips.schedule.ScheduleManager;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ForgeCommonConfig implements ICommonInGameTipsConfig {

    private final ForgeConfigSpec commonConfig;

    private final ForgeConfigSpec.ConfigValue<Integer> scheduleTime;
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> disabledNamespaces;
    private final ForgeConfigSpec.ConfigValue<Boolean> syncSending;
    private final ForgeConfigSpec.ConfigValue<Boolean> individualTips;
    private final ForgeConfigSpec.ConfigValue<Boolean> tipRecycling;

    public ForgeCommonConfig() {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        commonBuilder.push("Common configuration settings for InGameTips");

        scheduleTime = commonBuilder.comment("The time in seconds between each tip. Set to 0 to disable scheduled tips.\nExample: 60 (1 minute), 90 (1 minute 30 seconds), 300 (5 minutes)")
                .defineInRange("schedule_time", CommonConfig.getScheduleInterval(), 0, Integer.MAX_VALUE);
        disabledNamespaces = commonBuilder.comment("List of mod namespaces to disable tips from.\nExample: [\"modid\", \"datapackid\"]")
                .defineList("disabled_namespaces", CommonConfig.getDisabledNamespaces(), obj -> obj instanceof String);
        tipRecycling = commonBuilder.comment("If enabled, tips will be recycled when all tips have been sent.")
                .define("tip_recycling", CommonConfig.isRecyclingTips());

        commonBuilder.pop();

        commonBuilder.push("Synchronized sending");
        syncSending = commonBuilder.comment("If enabled, tips will be sent to all players at the same time.")
                .define("sync_sending", CommonConfig.isSyncSending());
        individualTips = commonBuilder.comment("If enabled, each player will receive a different tip. This option is only available if synchronized sendings is enabled.")
                .define("individual_tips", CommonConfig.isIndividualTips());

        commonBuilder.pop();
        commonConfig = commonBuilder.build();
    }

    /*
     * Getters
     */

    public ForgeConfigSpec getConfig() {
        return commonConfig;
    }

    @Override
    public int getScheduleInterval() {
        return scheduleTime.get();
    }

    @Override
    public List<String> getDisabledNamespaces() {
        return disabledNamespaces.get().stream().map(Object::toString).toList();
    }

    @Override
    public boolean isSyncSending() {
        return syncSending.get();
    }

    @Override
    public boolean isIndividualTips() {
        return individualTips.get();
    }

    @Override
    public boolean isRecyclingTips() {
        return tipRecycling.get();
    }

    /*
     * Setters
     */

    @Override
    public void setScheduleInterval(int scheduleInterval) {
        scheduleTime.set(scheduleInterval);
        refreshSchedules();
    }

    @Override
    public void setDisabledNamespaces(List<String> disabledNamespaces) {
        this.disabledNamespaces.set(disabledNamespaces);
    }

    @Override
    public void setRecyclingTips(boolean recyclingTips) {
        tipRecycling.set(recyclingTips);
    }

    @Override
    public void setSyncSending(boolean syncSending) {
        this.syncSending.set(syncSending);
        refreshSchedules();
    }

    @Override
    public void setIndividualTips(boolean individualTips) {
        this.individualTips.set(individualTips);
        refreshSchedules();
    }

    /*
     * Processors
     */

    private static void refreshSchedules() {
        if(InGameTips.SERVER != null) ScheduleManager.INSTANCE.refresh();
    }

}

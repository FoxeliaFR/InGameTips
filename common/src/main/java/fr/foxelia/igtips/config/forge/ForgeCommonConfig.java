package fr.foxelia.igtips.config.forge;

import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
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
                .defineInRange("scheduleTime", CommonConfig.getScheduleInterval(), 0, Integer.MAX_VALUE);
        disabledNamespaces = commonBuilder.comment("List of mod namespaces to disable tips from.\nExample: [\"modid\", \"datapackid\"]")
                .defineList("disabledNamespaces", CommonConfig.getDisabledNamespaces(), obj -> obj instanceof String);
        tipRecycling = commonBuilder.comment("If enabled, tips will be recycled when all tips have been sent.")
                .define("tipRecyling", CommonConfig.isRecyclingTips());

        commonBuilder.pop();

        commonBuilder.push("Synchronized sending");
        syncSending = commonBuilder.comment("If enabled, tips will be sent to all players at the same time.")
                .define("syncSending", CommonConfig.isSyncSending());
        individualTips = commonBuilder.comment("If enabled, each player will receive a different tip. This option is only available if synchronized sendings is enabled.")
                .define("individualTips", CommonConfig.isIndividualTips());

        commonBuilder.pop();
        commonConfig = commonBuilder.build();
    }

    public ForgeConfigSpec getConfig() {
        return commonConfig;
    }

    @Override
    public int getScheduleInterval() {
        return scheduleTime.get();
    }

    @Override
    public List<? extends String> getDisabledNamespaces() {
        return disabledNamespaces.get();
    }

    @Override
    public boolean getSyncSending() {
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
}

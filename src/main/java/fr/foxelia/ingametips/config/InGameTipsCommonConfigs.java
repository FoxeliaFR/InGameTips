package fr.foxelia.ingametips.config;

import fr.foxelia.ingametips.tip.PopUp;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class InGameTipsCommonConfigs {

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;

    /*
     * TODO:
     *  - Schedule time (every x minutes)
     *  - Sync tips with all players (All players receive the same tip at the same time)
     */
    public static ForgeConfigSpec.ConfigValue<Integer> scheduleTime;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> disabledNamespaces;
    public static ForgeConfigSpec.ConfigValue<Boolean> syncTipsWithAllPlayers;


    static {
        COMMON_BUILDER.push("Common configuration settings for InGameTips");

        scheduleTime = COMMON_BUILDER.comment("The time in seconds between each tip. Set to 0 to disable scheduled tips.\nExample: 60 (1 minute), 90 (1 minute 30 seconds), 300 (5 minutes)")
                .defineInRange("scheduleTime", 300, 0, Integer.MAX_VALUE);
        disabledNamespaces = COMMON_BUILDER.comment("List of mod namespaces to disable tips from.\nExample: [\"modid\", \"datapackid\"]")
                .defineList("disabledNamespaces", new ArrayList<>(), obj -> obj instanceof String);
        syncTipsWithAllPlayers = COMMON_BUILDER.comment("All players receive the same tip at the same time")
                .define("syncTipsWithAllPlayers", true);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}

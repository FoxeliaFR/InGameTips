package fr.foxelia.ingametips.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class InGameTipsCommonConfigs {

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;

    /*
     * TODO:
     *  - Schedule time (every x minutes)
     *  - Disable mod specific tips (Disable tips from a specific mod with a list of name spaces)
     *  - Sync tips with all players (All players receive the same tip at the same time)
     */
    public static ForgeConfigSpec.ConfigValue<Integer> scheduleTime;
    public static ForgeConfigSpec.ConfigValue<List<String>> disabledNamespaces;
    public static ForgeConfigSpec.ConfigValue<Boolean> syncTipsWithAllPlayers;


    static {
        COMMON_BUILDER.push("Common configuration settings for InGameTips");

        scheduleTime = COMMON_BUILDER.comment("The time in minutes between each tip. Set to 0 to disable scheduled tips.")
                .defineInRange("scheduleTime", 5, 0, Integer.MAX_VALUE);
        disabledNamespaces = COMMON_BUILDER.comment("List of mod namespaces to disable tips from")
                .define("disabledNamespaces", List.of());
        syncTipsWithAllPlayers = COMMON_BUILDER.comment("All players receive the same tip at the same time")
                .define("syncTipsWithAllPlayers", true);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}

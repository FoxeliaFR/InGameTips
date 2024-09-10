package fr.foxelia.igtips.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class InGameTipsCommonConfigs {

    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> scheduleTime;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> disabledNamespaces;
    public static ForgeConfigSpec.ConfigValue<Boolean> syncSendings;
    public static ForgeConfigSpec.ConfigValue<Boolean> individualTips;
    public static ForgeConfigSpec.ConfigValue<Boolean> tipRecyling;


    static {
        COMMON_BUILDER.push("Common configuration settings for InGameTips");

        scheduleTime = COMMON_BUILDER.comment("The time in seconds between each tip. Set to 0 to disable scheduled tips.\nExample: 60 (1 minute), 90 (1 minute 30 seconds), 300 (5 minutes)")
                .defineInRange("scheduleTime", 900, 0, Integer.MAX_VALUE);
        disabledNamespaces = COMMON_BUILDER.comment("List of mod namespaces to disable tips from.\nExample: [\"modid\", \"datapackid\"]")
                .defineList("disabledNamespaces", new ArrayList<>(), obj -> obj instanceof String);
        tipRecyling = COMMON_BUILDER.comment("If enabled, tips will be recycled when all tips have been sent.")
                .define("tipRecyling", false);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Synchronized sending");
        syncSendings = COMMON_BUILDER.comment("If enabled, tips will be sent to all players at the same time.")
                .define("syncSending", false);
        individualTips = COMMON_BUILDER.comment("If enabled, each player will receive a different tip. This option is only available if synchronized sendings is enabled.")
                .define("individualTips", false);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}

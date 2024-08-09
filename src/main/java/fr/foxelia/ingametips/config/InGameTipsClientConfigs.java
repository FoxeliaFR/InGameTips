package fr.foxelia.ingametips.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class InGameTipsClientConfigs {

    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Boolean> enableTips;
    public static ForgeConfigSpec.ConfigValue<Boolean> enableSound;
    public static ForgeConfigSpec.ConfigValue<Integer> maxLines;

    static {
        CLIENT_BUILDER.push("Client configuration settings for InGameTips");

        enableTips = CLIENT_BUILDER.comment("Enable or disable the display of tips in-game")
                .define("enableTips", true);
        enableSound = CLIENT_BUILDER.comment("Enable or disable the sound when a tip is displayed")
                .define("enableSound", true);
        maxLines = CLIENT_BUILDER.comment("Maximum number of lines displayed in a tip")
                .defineInRange("maxLines", 9, 1, 64);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

}

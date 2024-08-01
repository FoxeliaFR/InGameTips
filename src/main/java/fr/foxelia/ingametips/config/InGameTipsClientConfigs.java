package fr.foxelia.ingametips.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class InGameTipsClientConfigs {

    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Boolean> enableTips;


    static {
        CLIENT_BUILDER.push("Client configuration settings for InGameTips");

        enableTips = CLIENT_BUILDER.comment("Enable or disable the display of tips in-game")
                .define("enableTips", true);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

}

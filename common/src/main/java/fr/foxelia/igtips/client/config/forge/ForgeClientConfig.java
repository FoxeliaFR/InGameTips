package fr.foxelia.igtips.client.config.forge;

import fr.foxelia.igtips.client.config.ClientConfig;
import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeClientConfig implements IClientInGameTipsConfig {

    private final ForgeConfigSpec clientConfig;

    private final ForgeConfigSpec.ConfigValue<Boolean> enableTips;
    private final ForgeConfigSpec.ConfigValue<Boolean> enableSound;
    private final ForgeConfigSpec.ConfigValue<Integer> maxLines;

    public ForgeClientConfig() {
        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        clientBuilder.push("Client configuration settings for InGameTips");

        enableTips = clientBuilder.comment("Enable or disable the display of tips in-game")
                .define("enableTips", ClientConfig.isTipsEnabled());
        enableSound = clientBuilder.comment("Enable or disable the sound when a tip is displayed")
                .define("enableSound", ClientConfig.isSoundEnabled());
        maxLines = clientBuilder.comment("Maximum number of lines displayed in a tip")
                .defineInRange("maxLines", ClientConfig.getMaxLines(), 1, 64);

        clientBuilder.pop();
        clientConfig = clientBuilder.build();
    }

    public ForgeConfigSpec getConfig() {
        return clientConfig;
    }

    @Override
    public boolean isTipsEnabled() {
        return enableTips.get();
    }

    @Override
    public boolean isSoundEnabled() {
        return enableSound.get();
    }

    @Override
    public int getMaxLines() {
        return maxLines.get();
    }
}

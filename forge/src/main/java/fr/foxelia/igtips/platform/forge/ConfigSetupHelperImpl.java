package fr.foxelia.igtips.platform.forge;

import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import fr.foxelia.igtips.client.config.forge.ForgeClientConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import fr.foxelia.igtips.config.forge.ForgeCommonConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public class ConfigSetupHelperImpl {

    public static ICommonInGameTipsConfig setupCommonConfig() {
        ForgeCommonConfig commonConfig = new ForgeCommonConfig();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonConfig.getConfig(), MOD_ID + "-common.toml");
        return commonConfig;
    }

    public static IClientInGameTipsConfig setupClientConfig() {
        ForgeClientConfig clientConfig = new ForgeClientConfig();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientConfig.getConfig(), MOD_ID + "-client.toml");
        return clientConfig;
    }

}

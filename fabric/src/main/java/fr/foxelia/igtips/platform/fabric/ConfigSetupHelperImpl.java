package fr.foxelia.igtips.platform.fabric;

import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import fr.foxelia.igtips.client.config.forge.ForgeClientConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import fr.foxelia.igtips.config.forge.ForgeCommonConfig;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.fml.config.ModConfig;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public class ConfigSetupHelperImpl {

    public static ICommonInGameTipsConfig setupCommonConfig() {
        ForgeCommonConfig commonConfig = new ForgeCommonConfig();
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, commonConfig.getConfig(), MOD_ID + "-common.toml");
        return commonConfig;
    }

    public static IClientInGameTipsConfig setupClientConfig() {
        ForgeClientConfig clientConfig = new ForgeClientConfig();
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, clientConfig.getConfig(), MOD_ID + "-client.toml");
        return clientConfig;
    }

}

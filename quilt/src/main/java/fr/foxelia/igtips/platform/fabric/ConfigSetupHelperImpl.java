package fr.foxelia.igtips.platform.fabric;

import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import fr.foxelia.igtips.client.config.quilt.QuiltClientConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import fr.foxelia.igtips.config.quilt.QuiltCommonConfig;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public class ConfigSetupHelperImpl {

    public static ICommonInGameTipsConfig setupCommonConfig() {
        QuiltCommonConfig commonConfig = QuiltConfig.create(MOD_ID, MOD_ID + "-common", QuiltCommonConfig.class);
        return commonConfig;
    }

    public static IClientInGameTipsConfig setupClientConfig() {
        QuiltClientConfig clientConfig = QuiltConfig.create(MOD_ID, MOD_ID + "-client", QuiltClientConfig.class);
        return clientConfig;
    }

}

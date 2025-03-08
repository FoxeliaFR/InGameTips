package fr.foxelia.igtips.platform.fabric;

import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import fr.foxelia.igtips.quilt.client.config.QuiltClientConfig;
import fr.foxelia.igtips.quilt.config.QuiltCommonConfig;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public class ConfigSetupHelperImpl {

    public static ICommonInGameTipsConfig setupCommonConfig() {
        return QuiltConfig.create(MOD_ID, MOD_ID + "-common", QuiltCommonConfig.class);
    }

    public static IClientInGameTipsConfig setupClientConfig() {
        return QuiltConfig.create(MOD_ID, MOD_ID + "-client", QuiltClientConfig.class);
    }

}

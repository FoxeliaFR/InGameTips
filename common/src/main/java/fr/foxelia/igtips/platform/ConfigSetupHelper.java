package fr.foxelia.igtips.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;

public class ConfigSetupHelper {

    @ExpectPlatform
    public static ICommonInGameTipsConfig setupCommonConfig() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static IClientInGameTipsConfig setupClientConfig() {
        throw new AssertionError();
    }

}

package fr.foxelia.igtips.fabric;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.InGameTipsCommonConfigs;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public final class InGameTipsFabric implements ModInitializer {

    /*
     * TODO:
     *  - DatapackLoader
     *  - ConfigUpdateHandler
     *  - PlayerLanguageHelper
     */

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        InGameTips.init();

        // Register the common config
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, InGameTipsCommonConfigs.COMMON_CONFIG, MOD_ID + "-common.toml");

    }
}

package fr.foxelia.igtips.fabric;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.ConfigUpdateHandler;
import fr.foxelia.igtips.config.InGameTipsCommonConfigs;
import fr.foxelia.igtips.datapack.TipLoader;
import fr.foxelia.igtips.fabric.datapack.DatapackLoader;
import fr.foxelia.igtips.network.fabric.PlayerLanguagePacket;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraftforge.fml.config.ModConfig;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public final class InGameTipsFabric implements ModInitializer {

    /*
     * TODO:
     *  - PlayerLanguageHelper
     */

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        InGameTips.init();

        // Register language packet
        PlayerLanguagePacket.registerServerPacketHandler();

        // Register the common config
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, InGameTipsCommonConfigs.COMMON_CONFIG, MOD_ID + "-common.toml");

        // Event registration
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new DatapackLoader());
        ModConfigEvents.reloading(MOD_ID).register(ConfigUpdateHandler::configChanged);

    }
}

package fr.foxelia.igtips.fabric;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.forge.ForgeConfigUpdateHandler;
import fr.foxelia.igtips.fabric.network.PlayerLanguagePacket;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

public final class InGameTipsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        InGameTips.init();

        // Register language packet
        PlayerLanguagePacket.registerServerPacketHandler();

        // Event registration
        ModConfigEvents.reloading(MOD_ID).register(ForgeConfigUpdateHandler::configChanged);

    }
}

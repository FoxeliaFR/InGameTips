package fr.foxelia.igtips.fabric.client;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.client.config.InGameTipsClientConfigs;
import fr.foxelia.igtips.network.fabric.PlayerLanguagePacket;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraftforge.fml.config.ModConfig;

public final class InGameTipsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the client config
        ForgeConfigRegistry.INSTANCE.register(InGameTips.MOD_ID, ModConfig.Type.CLIENT, InGameTipsClientConfigs.CLIENT_CONFIG, InGameTips.MOD_ID + "-client.toml");

        // Update the player's language when they join the server
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> PlayerLanguagePacket.sendLanguageToServer(client.getLanguageManager().getLanguage()));
    }
}

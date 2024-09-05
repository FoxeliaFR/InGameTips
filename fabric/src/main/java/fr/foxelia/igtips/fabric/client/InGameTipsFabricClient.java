package fr.foxelia.igtips.fabric.client;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.client.config.InGameTipsClientConfigs;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public final class InGameTipsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register the client config
        ForgeConfigRegistry.INSTANCE.register(InGameTips.MOD_ID, ModConfig.Type.CLIENT, InGameTipsClientConfigs.CLIENT_CONFIG, InGameTips.MOD_ID + "-client.toml");
    }
}

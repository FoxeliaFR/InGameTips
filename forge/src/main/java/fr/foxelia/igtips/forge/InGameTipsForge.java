package fr.foxelia.igtips.forge;

import dev.architectury.platform.forge.EventBuses;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.client.config.InGameTipsClientConfigs;
import fr.foxelia.igtips.config.InGameTipsCommonConfigs;
import fr.foxelia.igtips.forge.event.ModConfigReloadingEventHandler;
import fr.foxelia.igtips.forge.datapack.DatapackLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

@Mod(MOD_ID)
public final class InGameTipsForge {
    public InGameTipsForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        InGameTips.init();

        // Register the config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, InGameTipsClientConfigs.CLIENT_CONFIG, MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, InGameTipsCommonConfigs.COMMON_CONFIG, MOD_ID + "-common.toml");

        // Register ourselves for server and other game events we are interested in
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.addListener(DatapackLoader::onAddReloadListener);
        eventBus.addListener(ModConfigReloadingEventHandler::onConfigChanged);
    }

}

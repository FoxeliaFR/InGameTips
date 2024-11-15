package fr.foxelia.igtips.forge;

import dev.architectury.platform.forge.EventBuses;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.forge.event.ModConfigReloadingEventHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

@Mod(MOD_ID)
public final class InGameTipsForge {

    public InGameTipsForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(MOD_ID, modEventBus);

        // Run our common setup.
        InGameTips.init();
        InGameTips.initClient();

        // Register the mod config reloading event handler.
        modEventBus.addListener(ModConfigReloadingEventHandler::onConfigChanged);
    }

}

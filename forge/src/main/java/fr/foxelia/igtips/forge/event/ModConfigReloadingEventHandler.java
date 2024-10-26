package fr.foxelia.igtips.forge.event;

import fr.foxelia.igtips.config.forge.ForgeConfigUpdateHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ModConfigReloadingEventHandler {

    @SubscribeEvent
    public static void onConfigChanged(ModConfigEvent.Reloading event) {
        ForgeConfigUpdateHandler.configChanged(event.getConfig());
    }

}

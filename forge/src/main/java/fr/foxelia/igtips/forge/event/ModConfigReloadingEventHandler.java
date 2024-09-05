package fr.foxelia.igtips.forge.event;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.ConfigUpdateHandler;
import fr.foxelia.igtips.schedule.ScheduleManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ModConfigReloadingEventHandler {

    @SubscribeEvent
    public static void onConfigChanged(ModConfigEvent.Reloading event) {
        ConfigUpdateHandler.configChanged(event.getConfig());
    }

}

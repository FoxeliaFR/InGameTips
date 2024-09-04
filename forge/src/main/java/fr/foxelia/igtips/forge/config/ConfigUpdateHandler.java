package fr.foxelia.igtips.forge.config;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.schedule.ScheduleManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ConfigUpdateHandler {

    @SubscribeEvent
    public static void onConfigChanged(ModConfigEvent.Reloading event) {
        if (event.getConfig().getFileName().equals(InGameTips.MOD_ID + "-common.toml")) {
            if (InGameTips.SERVER != null) ScheduleManager.INSTANCE.refresh();
        }
    }

}

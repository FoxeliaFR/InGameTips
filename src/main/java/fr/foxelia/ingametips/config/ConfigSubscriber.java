package fr.foxelia.ingametips.config;

import fr.foxelia.ingametips.InGameTips;
import fr.foxelia.ingametips.schedule.ScheduleManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = InGameTips.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigSubscriber {

    @SubscribeEvent
    public static void onConfigChanged(ModConfigEvent.Reloading event) {
        if (event.getConfig().getFileName().equals(InGameTips.MOD_ID + "-common.toml")) {
            // Only on server side
            if (InGameTips.SERVER == null) return;
            ScheduleManager.INSTANCE.refresh();


        }
    }

}

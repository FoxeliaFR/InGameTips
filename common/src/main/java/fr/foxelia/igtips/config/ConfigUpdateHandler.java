package fr.foxelia.igtips.config;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.schedule.ScheduleManager;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigUpdateHandler {
    public static void configChanged(ModConfig config) {
        if (config.getFileName().equals(InGameTips.MOD_ID + "-common.toml")) {
            if (InGameTips.SERVER != null) ScheduleManager.INSTANCE.refresh();
        }
    }
}

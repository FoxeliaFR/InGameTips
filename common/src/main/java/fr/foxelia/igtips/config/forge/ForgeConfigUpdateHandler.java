package fr.foxelia.igtips.config.forge;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.CommonConfigManager;
import fr.foxelia.igtips.schedule.ScheduleManager;
import net.minecraftforge.fml.config.ModConfig;

public class ForgeConfigUpdateHandler {
    public static void configChanged(ModConfig config) {
        if (config.getFileName().equals(InGameTips.MOD_ID + "-common.toml")) {
            if (InGameTips.SERVER != null) {
                // Refresh the schedule manager
                ScheduleManager.INSTANCE.refresh();
                // Update the config to all players
                InGameTips.SERVER.getPlayerManager().getPlayerList().forEach(player -> {
                    CommonConfigManager.syncConfigToPlayer(player);
                });
            }
        }
    }
}

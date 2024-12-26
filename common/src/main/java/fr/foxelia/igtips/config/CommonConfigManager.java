package fr.foxelia.igtips.config;

import fr.foxelia.igtips.network.ConfigPacket;
import fr.foxelia.igtips.network.NetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommonConfigManager {

    /*
     * TODO:
     *  - S: Update the config on the server side.
     *  - S: Reload schedules on update.
     *  - S: Save the config to the disk.
     *  - C: Unload the server config on disconnect / on deop.
     *  - TEST: Update the client config on file change / any change . (Quilt, Fabric, Forge)
     */

    public static ICommonInGameTipsConfig SERVER_CONFIG = null;

    public static void syncConfigToPlayer(ServerPlayerEntity player) {
        if(player.hasPermissionLevel(3)) {
            NetworkHandler.CHANNEL.sendToPlayer(player, new ConfigPacket(
                    CommonConfig.getScheduleInterval(),
                    CommonConfig.getDisabledNamespaces(),
                    CommonConfig.isSyncSending(),
                    CommonConfig.isIndividualTips(),
                    CommonConfig.isRecyclingTips()
            ));
        }
    }

}

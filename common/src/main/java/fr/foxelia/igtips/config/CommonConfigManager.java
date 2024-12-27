package fr.foxelia.igtips.config;

import fr.foxelia.igtips.network.ConfigPacket;
import fr.foxelia.igtips.network.NetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class CommonConfigManager {

    /*
     * TODO:
     *  - TEST: Update the config on the server side.
     *  - S: Reload schedules on update.
     *  - S: Save the config to the disk.
     *  - TEST: Unload the server config on disconnect / on deop.
     *  - TEST: Update the client config on file change / any change . (Quilt, Fabric, Forge)
     */

    public static final int EDIT_PERMISSION_LEVEL = 3;
    public static ICommonInGameTipsConfig SERVER_CONFIG = null;

    public static void syncConfigToPlayer(ServerPlayerEntity player) {
        if(player.hasPermissionLevel(EDIT_PERMISSION_LEVEL)) {
            syncConfigToPlayer(player, true);
        }
    }

    public static void syncConfigToPlayer(ServerPlayerEntity player, boolean force) {
        if(force) {
            NetworkHandler.CHANNEL.sendToPlayer(player, new ConfigPacket(
                    CommonConfig.getScheduleInterval(),
                    CommonConfig.getDisabledNamespaces(),
                    CommonConfig.isSyncSending(),
                    CommonConfig.isIndividualTips(),
                    CommonConfig.isRecyclingTips(),
                    false
            ));
        } else {
            syncConfigToPlayer(player);
        }
    }

    public static void unsyncConfigFromPlayer(ServerPlayerEntity player) {
        NetworkHandler.CHANNEL.sendToPlayer(player, new ConfigPacket(
                0,
                List.of(),
                false,
                false,
                false,
                true
        ));
    }

}

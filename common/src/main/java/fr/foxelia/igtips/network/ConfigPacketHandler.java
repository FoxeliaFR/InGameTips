package fr.foxelia.igtips.network;

import dev.architectury.networking.NetworkManager;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.config.CommonConfigManager;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import net.fabricmc.api.EnvType;

import java.util.List;
import java.util.function.Supplier;

public class ConfigPacketHandler {

    public static void handle(ConfigPacket packet, Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext ctx = contextSupplier.get();
        ctx.queue(() -> {
            if(ctx.getEnv().equals(EnvType.SERVER)) {
                // Check if the player is allowed to change the config and applying it.
                if(ctx.getPlayer().hasPermissionLevel(3)) {
                    // Update the config on the server side.
                    CommonConfig.setScheduleInterval(packet.scheduleInterval());
                    CommonConfig.setDisabledNamespaces(packet.disabledNamespaces());
                    CommonConfig.setSyncSending(packet.syncSending());
                    CommonConfig.setIndividualTips(packet.individualTips());
                    CommonConfig.setRecyclingTips(packet.recyclingTips());

                    // Send the updated config to all connected clients with correct permission level.
                    InGameTips.SERVER.getPlayerManager().getPlayerList().forEach(player -> CommonConfigManager.syncConfigToPlayer(player));
                }
            } else {
                if(packet.unsync()) {
                    CommonConfigManager.SERVER_CONFIG = null;
                } else CommonConfigManager.SERVER_CONFIG = new ICommonInGameTipsConfig() {
                    @Override
                    public int getScheduleInterval() {
                        return packet.scheduleInterval();
                    }

                    @Override
                    public List<String> getDisabledNamespaces() {
                        return packet.disabledNamespaces();
                    }

                    @Override
                    public boolean isSyncSending() {
                        return packet.syncSending();
                    }

                    @Override
                    public boolean isIndividualTips() {
                        return packet.individualTips();
                    }

                    @Override
                    public boolean isRecyclingTips() {
                        return packet.recyclingTips();
                    }
                };
            }
        });
    }

}

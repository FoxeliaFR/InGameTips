package fr.foxelia.igtips.event;

import dev.architectury.event.events.common.PlayerEvent;
import fr.foxelia.igtips.config.CommonConfigManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class ConfigEventHandler {

    public static void register() {
        PlayerEvent.PLAYER_JOIN.register(ConfigEventHandler::onPlayerJoin);
    }

    public static void onPlayerJoin(ServerPlayerEntity player) {
        CommonConfigManager.syncConfigToPlayer(player);
    }

}

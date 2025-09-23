package fr.foxelia.igtips.event;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.data.HistoryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.WorldSavePath;

public class ServerEventHandler {

    public static void register() {
        LifecycleEvent.SERVER_STARTING.register(ServerEventHandler::onServerStarting);
        PlayerEvent.PLAYER_QUIT.register(ServerEventHandler::onPlayerLeave);
    }

    public static void onServerStarting(MinecraftServer server) {
        InGameTips.SERVER = server;
        HistoryManager.registerWorldData(server.getSavePath(WorldSavePath.ROOT));
    }

    public static void onPlayerLeave(ServerPlayerEntity player) {
        HistoryManager.unCacheHistory(player.getUuidAsString());
    }
}

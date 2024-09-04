package fr.foxelia.igtips.event;

import dev.architectury.event.events.common.LifecycleEvent;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.data.TipHistory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

public class ServerEventHandler {

    public static void register() {
        LifecycleEvent.SERVER_STARTING.register(ServerEventHandler::onServerStarting);
    }

    public static void onServerStarting(MinecraftServer server) {
        InGameTips.SERVER = server;
        TipHistory.registerWorldData(server.getSavePath(WorldSavePath.ROOT));
    }
}

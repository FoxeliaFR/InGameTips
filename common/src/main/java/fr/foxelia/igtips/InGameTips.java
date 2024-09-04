package fr.foxelia.igtips;

import dev.architectury.platform.Platform;
import fr.foxelia.igtips.client.event.OverlayEventHandler;
import fr.foxelia.igtips.event.CommandEventHandler;
import fr.foxelia.igtips.event.ScheduleEventHandler;
import fr.foxelia.igtips.event.ServerEventHandler;
import fr.foxelia.igtips.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InGameTips {
    public static final String MOD_ID = "igtips";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static MinecraftServer SERVER = null;

    public static void init() {
        ServerEventHandler.register();
        CommandEventHandler.registerCommands();
        ScheduleEventHandler.register();
        NetworkHandler.registerPackets();

        if(Platform.getEnv().equals(EnvType.CLIENT)) {
            initClient();
        }

    }

    private static void initClient() {
        OverlayEventHandler.register();
    }
}
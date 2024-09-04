package fr.foxelia.igtips.event;

import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import fr.foxelia.igtips.schedule.ScheduleManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ScheduleEventHandler {

    public static void register() {
        PlayerEvent.PLAYER_JOIN.register(ScheduleEventHandler::onPlayerJoin);
        PlayerEvent.PLAYER_QUIT.register(ScheduleEventHandler::onPlayerLeave);
        TickEvent.SERVER_POST.register(ScheduleEventHandler::onServerTick);
    }

    public static void onPlayerJoin(ServerPlayerEntity player) {
        ScheduleManager.INSTANCE.connect(player);
    }

    public static void onPlayerLeave(ServerPlayerEntity player) {
        ScheduleManager.INSTANCE.disconnect(player.getUuid());
    }

    public static void onServerTick(MinecraftServer server) {
        ScheduleManager.INSTANCE.tick();
    }

}



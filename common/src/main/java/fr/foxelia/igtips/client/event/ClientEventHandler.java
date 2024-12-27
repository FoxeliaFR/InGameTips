package fr.foxelia.igtips.client.event;

import dev.architectury.event.events.client.ClientPlayerEvent;
import fr.foxelia.igtips.config.CommonConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ClientEventHandler {

    public static void register() {
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(ClientEventHandler::onPlayerQuit);
    }

    private static void onPlayerQuit(@Nullable ClientPlayerEntity clientPlayerEntity) {
        if(clientPlayerEntity == null) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc == null) return;
        if(clientPlayerEntity == mc.player) {
            CommonConfigManager.SERVER_CONFIG = null;
        }
    }

}

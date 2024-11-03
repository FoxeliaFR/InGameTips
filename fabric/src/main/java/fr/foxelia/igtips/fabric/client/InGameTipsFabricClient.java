package fr.foxelia.igtips.fabric.client;

import fr.foxelia.igtips.fabric.network.PlayerLanguagePacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public final class InGameTipsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Update the player's language when they join the server
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> PlayerLanguagePacket.sendLanguageToServer(client.getLanguageManager().getLanguage()));
    }
}

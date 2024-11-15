package fr.foxelia.igtips.quilt.client;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.quilt.network.PlayerLanguagePacket;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

public class InGameTipsQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        // Run our common client setup.
        InGameTips.initClient();

        // Update the player's language when they join the server
        ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> PlayerLanguagePacket.sendLanguageToServer(client.getLanguageManager().getLanguage())));
    }
}

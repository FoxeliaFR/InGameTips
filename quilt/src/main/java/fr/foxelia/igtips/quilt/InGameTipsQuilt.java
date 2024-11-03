package fr.foxelia.igtips.quilt;

import fr.foxelia.igtips.network.quilt.PlayerLanguagePacket;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import fr.foxelia.igtips.InGameTips;

public final class InGameTipsQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        // Run our common setup.
        InGameTips.init();

        // Register language packet
        PlayerLanguagePacket.registerServerPacketHandler();


    }
}

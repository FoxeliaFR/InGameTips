package fr.foxelia.ingametips.network;

import fr.foxelia.ingametips.InGameTips;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class InGameTipsPacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(InGameTips.MOD_ID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int id = 0;

        CHANNEL.registerMessage(id++, TipPacket.class, TipPacket::encode, TipPacket::decode, TipPacketHandler::handle);
    }
}

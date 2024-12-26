package fr.foxelia.igtips.network;

import dev.architectury.networking.NetworkChannel;
import fr.foxelia.igtips.InGameTips;
import net.minecraft.util.Identifier;

public class NetworkHandler {

    public static final NetworkChannel CHANNEL = NetworkChannel.create(new Identifier(InGameTips.MOD_ID, "main"));

    public static void registerPackets() {
        CHANNEL.register(TipPacket.class, TipPacket::encode, TipPacket::decode, TipPacketHandler::handle);
        CHANNEL.register(ConfigPacket.class, ConfigPacket::encode, ConfigPacket::decode, ConfigPacketHandler::handle);
    }

}

package fr.foxelia.igtips.network.fabric;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.mixin.fabric.PlayerLanguageAccessor;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class PlayerLanguagePacket {

    public static final Identifier LANGUAGE_PACKET_ID = new Identifier(InGameTips.MOD_ID, "player_language");

    public static void registerServerPacketHandler() {
        ServerPlayNetworking.registerGlobalReceiver(LANGUAGE_PACKET_ID, PlayerLanguagePacket::handle);
    }

    public static void sendLanguageToServer(String languageCode, ClientPlayNetworking networking) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(languageCode);
        networking.send(LANGUAGE_PACKET_ID, buf);

    }

    protected static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler ctx, PacketByteBuf buf, PacketSender sender) {
        String language = buf.readString();
        ((PlayerLanguageAccessor) player).setLanguage(language);
    }


}

package fr.foxelia.igtips.network.quilt;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.accessor.PlayerLanguageAccessor;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class PlayerLanguagePacket {

    public static final Identifier LANGUAGE_PACKET_ID = new Identifier(InGameTips.MOD_ID, "player_language");

    public static void registerServerPacketHandler() {
        ServerPlayNetworking.registerGlobalReceiver(LANGUAGE_PACKET_ID, PlayerLanguagePacket::handle);
    }

    public static void sendLanguageToServer(String languageCode) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(languageCode);
        ClientPlayNetworking.send(LANGUAGE_PACKET_ID, buf);
    }

    protected static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler ctx, PacketByteBuf buf, PacketSender sender) {
        String language = buf.readString();
        PlayerLanguageAccessor playerLanguageAccessor = (PlayerLanguageAccessor) player;
        playerLanguageAccessor.setLanguage(language);
    }


}

package fr.foxelia.igtips.network;

import dev.architectury.networking.NetworkManager;
import fr.foxelia.igtips.client.overlay.PopUpManager;
import fr.foxelia.igtips.tip.PopUp;
import net.fabricmc.api.EnvType;

import java.util.function.Supplier;

public class TipPacketHandler {

    public static void handle(TipPacket packet, Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext ctx = contextSupplier.get();
        ctx.queue(() -> {
            if(ctx.getEnv().equals(EnvType.CLIENT)) {
                PopUpManager.getInstance().queue(new PopUp(packet.tip().message(), packet.tip().displayTime()));
            }
        });
    }
}

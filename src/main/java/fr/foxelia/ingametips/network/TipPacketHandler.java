package fr.foxelia.ingametips.network;

import fr.foxelia.ingametips.overlay.PopUpManager;
import fr.foxelia.ingametips.tip.PopUp;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TipPacketHandler {

    public static void handle(TipPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();
            if (direction == NetworkDirection.PLAY_TO_CLIENT) {
                PopUpManager.getInstance().queue(new PopUp(packet.tip().message(), packet.tip().displayTime()));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

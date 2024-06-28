package fr.foxelia.ingametips.network;

import fr.foxelia.ingametips.PopUpManager;
import fr.foxelia.ingametips.client.PopUp;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TipPacketHandler {

    public static void handle(TipPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();
            if (direction == NetworkDirection.PLAY_TO_CLIENT) {
                PopUpManager.getInstance().queue(new PopUp(packet.tip().getTip(), packet.tip().getDisplayTime()));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}

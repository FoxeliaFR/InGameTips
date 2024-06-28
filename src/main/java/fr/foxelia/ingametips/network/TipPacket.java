package fr.foxelia.ingametips.network;

import fr.foxelia.ingametips.client.PopUp;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public record TipPacket(@NotNull PopUp tip) {

    public TipPacket {
        if (tip.getTip().length() > 32767) {
            throw new IllegalArgumentException("Message is too long");
        }
        if (tip.getDisplayTime() < 0) {
            throw new IllegalArgumentException("Display time is negative");
        }
    }

    public static void encode(TipPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.tip().getTip());
        buffer.writeInt(packet.tip().getDisplayTime());
    }

    public static TipPacket decode(FriendlyByteBuf buffer) {
        return new TipPacket(new PopUp(buffer.readUtf(32767), buffer.readInt()));
    }

}

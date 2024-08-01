package fr.foxelia.ingametips.network;

import fr.foxelia.ingametips.tip.ITip;
import fr.foxelia.ingametips.tip.PopUp;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public record TipPacket(@NotNull ITip tip) {

    public TipPacket {
        if (tip.getMessage().length() > 32767) {
            throw new IllegalArgumentException("Message is too long");
        }
        if (tip.getDisplayTime() < 0) {
            throw new IllegalArgumentException("Display time is negative");
        }
    }

    public static void encode(TipPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.tip().getMessage());
        buffer.writeInt(packet.tip().getDisplayTime());
    }

    public static TipPacket decode(FriendlyByteBuf buffer) {
        return new TipPacket(new PopUp(buffer.readUtf(32767), buffer.readInt()));
    }

}

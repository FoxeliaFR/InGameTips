package fr.foxelia.igtips.network;

import fr.foxelia.igtips.tip.ITip;
import fr.foxelia.igtips.tip.PopUp;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

public record TipPacket(@NotNull ITip tip) {

    public TipPacket {
        if (tip.message().length() > 32767) {
            throw new IllegalArgumentException("Message is too long");
        }
        if (tip.displayTime() < 0) {
            throw new IllegalArgumentException("Display time is negative");
        }
    }

    public void encode(PacketByteBuf buffer) {
        buffer.writeString(tip.message());
        buffer.writeInt(tip.displayTime());
    }

    public static TipPacket decode(PacketByteBuf buffer) {
        return new TipPacket(new PopUp(buffer.readString(32767), buffer.readInt()));
    }
}

package fr.foxelia.igtips.network;

import net.minecraft.network.PacketByteBuf;

import java.util.List;

public record ConfigPacket(int scheduleInterval, List<String> disabledNamespaces, boolean syncSending, boolean individualTips, boolean recyclingTips, boolean unsync) {

    public void encode(PacketByteBuf buffer) {
        buffer.writeBoolean(unsync);
        buffer.writeInt(scheduleInterval);
        buffer.writeString(String.join(",", disabledNamespaces));
        buffer.writeBoolean(syncSending);
        buffer.writeBoolean(individualTips);
        buffer.writeBoolean(recyclingTips);
    }

    public static ConfigPacket decode(PacketByteBuf buffer) {
        if(buffer.readBoolean()) {
            return new ConfigPacket(0, List.of(), false, false, false, true);
        }
        int scheduleInterval = buffer.readInt();
        List<String> disabledNamespaces = List.of(buffer.readString().split(","));
        boolean syncSending = buffer.readBoolean();
        boolean individualTips = buffer.readBoolean();
        boolean recyclingTips = buffer.readBoolean();
        return new ConfigPacket(scheduleInterval, disabledNamespaces, syncSending, individualTips, recyclingTips, false);
    }
}

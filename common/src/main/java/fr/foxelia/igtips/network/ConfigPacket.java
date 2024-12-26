package fr.foxelia.igtips.network;

import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import net.minecraft.network.PacketByteBuf;

import java.util.List;

public record ConfigPacket(int scheduleInterval, List<String> disabledNamespaces, boolean syncSending, boolean individualTips, boolean recyclingTips) {

    public void encode(PacketByteBuf buffer) {
        buffer.writeInt(scheduleInterval);
        buffer.writeString(String.join(",", disabledNamespaces));
        buffer.writeBoolean(syncSending);
        buffer.writeBoolean(individualTips);
        buffer.writeBoolean(recyclingTips);
    }

    public static ConfigPacket decode(PacketByteBuf buffer) {
        int scheduleInterval = buffer.readInt();
        List<String> disabledNamespaces = List.of(buffer.readString().split(","));
        boolean syncSending = buffer.readBoolean();
        boolean individualTips = buffer.readBoolean();
        boolean recyclingTips = buffer.readBoolean();
        return new ConfigPacket(scheduleInterval, disabledNamespaces, syncSending, individualTips, recyclingTips);
    }
}

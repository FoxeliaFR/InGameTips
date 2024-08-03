package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.network.InGameTipsPacketHandler;
import fr.foxelia.ingametips.network.TipPacket;
import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class CommonSchedule extends Schedule {

    public CommonSchedule() {
        super("common");
    }

    @Override
    protected void execute() {

    }
}

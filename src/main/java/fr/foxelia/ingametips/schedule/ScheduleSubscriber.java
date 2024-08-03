package fr.foxelia.ingametips.schedule;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScheduleSubscriber {

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if(event.getLevel().isClientSide) return;

        if(event.getEntity() instanceof ServerPlayer p) {
            ScheduleManager.INSTANCE.connect(p);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(EntityLeaveLevelEvent event) {
        if(event.getLevel().isClientSide) return;
        if(event.getEntity() instanceof ServerPlayer p) {
            ScheduleManager.INSTANCE.disconnect(p.getUUID());
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        ScheduleManager.INSTANCE.tick();
    }

}

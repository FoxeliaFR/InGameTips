package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.config.InGameTipsCommonConfigs;
import fr.foxelia.ingametips.data.TipHistory;
import net.minecraft.server.level.ServerPlayer;
import org.checkerframework.checker.units.qual.C;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScheduleManager {

    public static final ScheduleManager INSTANCE = new ScheduleManager();

    protected static final String COMMON_SCHEDULE = "common";

    private Map<String, Schedule> schedules = new HashMap<>();

    public void connect(ServerPlayer player) {
        if(InGameTipsCommonConfigs.syncSendings.get()) {
            if(InGameTipsCommonConfigs.individualTips.get()) {
                Schedule copyFrom = schedules.isEmpty() ? null : schedules.values().iterator().next();
                PlayerSchedule schedule = createPlayerSchedule(player);
                if(copyFrom != null) schedule.syncWithSchedule(copyFrom);
            } else {
                if(!schedules.containsKey(COMMON_SCHEDULE)) {
                    createCommonSchedule();
                }
            }
        } else createPlayerSchedule(player);
    }

    public void disconnect(UUID player) {
        String key = player.toString();
        if(schedules.containsKey(key)) {
            schedules.remove(key);
        }
    }

    public void tick() {
        for(Schedule schedule : schedules.values()) {
            schedule.run();
        }
    }

    private void createCommonSchedule() {
        schedules.clear();
        schedules.put(COMMON_SCHEDULE, new CommonSchedule());
    }

    private PlayerSchedule createPlayerSchedule(ServerPlayer player) {
        PlayerSchedule playerSchedule = new PlayerSchedule(player);
        schedules.put(player.getStringUUID(), playerSchedule);
        return playerSchedule;
    }





}

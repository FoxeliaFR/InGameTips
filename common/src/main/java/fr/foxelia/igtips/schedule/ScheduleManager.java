package fr.foxelia.igtips.schedule;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.InGameTipsCommonConfigs;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScheduleManager {

    public static final ScheduleManager INSTANCE = new ScheduleManager();

    protected static final String COMMON_SCHEDULE = "common";

    private final Map<String, Schedule> schedules = new HashMap<>();

    public void connect(ServerPlayerEntity player) {
        if(InGameTipsCommonConfigs.scheduleTime.get() == 0) return;
        if(InGameTipsCommonConfigs.syncSendings.get()) {
            if(InGameTipsCommonConfigs.individualTips.get()) {
                Schedule copyFrom = getFirstSchedule();
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
        schedules.remove(key);
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

    private PlayerSchedule createPlayerSchedule(ServerPlayerEntity player) {
        PlayerSchedule playerSchedule = new PlayerSchedule(player);
        schedules.put(player.getUuidAsString(), playerSchedule);
        return playerSchedule;
    }

    @Nullable
    private Schedule getFirstSchedule() {
        return schedules.isEmpty() ? null : schedules.values().iterator().next();
    }

    public void refresh() {
        if(InGameTipsCommonConfigs.scheduleTime.get() == 0) {
            schedules.clear();
            return;
        }
        if (InGameTipsCommonConfigs.syncSendings.get()) {
            if (InGameTipsCommonConfigs.individualTips.get()) {
                handleIndividualTips();
            } else {
                handleCommonSchedule();
            }
        } else {
            updateAllSchedules();
            reconnectServer();
        }
    }

    private void handleIndividualTips() {
        if (schedules.containsKey(COMMON_SCHEDULE)) {
            schedules.remove(COMMON_SCHEDULE);
            reconnectServer();
        } else {
            Schedule copyFrom = getFirstSchedule();
            for (Schedule schedule : schedules.values()) {
                schedule.updateExecutionTick();
                schedule.syncWithSchedule(copyFrom);
            }
        }
    }

    private void handleCommonSchedule() {
        if (!schedules.containsKey(COMMON_SCHEDULE)) {
            createCommonSchedule();
        } else {
            updateAllSchedules();
        }
    }

    private void updateAllSchedules() {
        for (Schedule schedule : schedules.values()) {
            schedule.updateExecutionTick();
        }
    }

    private void reconnectServer() {
        for (ServerPlayerEntity player : InGameTips.SERVER.getPlayerManager().getPlayerList()) {
            if(!schedules.containsKey(player.getUuidAsString())) connect(player);
        }
    }






}

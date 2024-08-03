package fr.foxelia.ingametips.schedule;

import fr.foxelia.ingametips.data.TipHistory;
import net.minecraft.server.level.ServerPlayer;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScheduleManager {

    public static final ScheduleManager INSTANCE = new ScheduleManager();

    private Map<String, Schedule> schedules = new HashMap<>();

    public void connect(ServerPlayer player) {
        schedules.put(player.toString(), new PlayerSchedule(player));
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





}

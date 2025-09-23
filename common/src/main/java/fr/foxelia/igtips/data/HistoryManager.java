package fr.foxelia.igtips.data;

import fr.foxelia.igtips.InGameTips;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HistoryManager {

    public static Path HISTORY_PATH = null;

    private static final Map<String, TipHistory> historyMap = new HashMap<>();

    public static TipHistory getHistoryOf(String viewerId) {
        return historyMap.computeIfAbsent(viewerId, TipHistory::new);
    }

    public static void saveAllHistories() {
        for (TipHistory history : historyMap.values()) {
            history.saveHistory();
        }
    }

    public static void unCacheHistory(String viewerId) {
        TipHistory history = historyMap.remove(viewerId);
        if (history != null) {
            history.saveHistory();
        }
    }

    public static void registerWorldData(Path worldPath) {
        HISTORY_PATH = Paths.get(worldPath.toString(), "data", InGameTips.MOD_ID, "history");
    }

}

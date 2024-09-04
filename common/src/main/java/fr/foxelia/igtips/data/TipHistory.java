package fr.foxelia.igtips.data;

import fr.foxelia.igtips.InGameTips;
import net.minecraft.util.Identifier;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class TipHistory {

    private static Path HISTORY_PATH = null;

    private final File historyFile;
    private final Set<Identifier> viewedTips;

    public TipHistory(String viewerId) {
        this.viewedTips = new HashSet<>();
        historyFile = new File(HISTORY_PATH.toString(), viewerId + ".history");
        loadHistory();
    }

    public void addTip(Identifier tipId) {
        viewedTips.add(tipId);
    }

    public boolean hasViewed(Identifier tipId) {
        return viewedTips.contains(tipId);
    }

    public Set<Identifier> getViewedTips() {
        return viewedTips;
    }

    public void saveHistory() {
        historyFile.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile))) {
            for (Identifier tip : viewedTips) {
                writer.write(tip.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {
        if (historyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    viewedTips.add(new Identifier(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerWorldData(Path worldPath) {
        HISTORY_PATH = Paths.get(worldPath.toString(), "data", InGameTips.MOD_ID, "history");
    }
}

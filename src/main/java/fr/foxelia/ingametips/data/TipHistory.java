package fr.foxelia.ingametips.data;

import fr.foxelia.ingametips.InGameTips;
import net.minecraft.resources.ResourceLocation;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class TipHistory {

    private static Path HISTORY_PATH = null;

    private final File historyFile;
    private final Set<ResourceLocation> viewedTips;

    public TipHistory(String viewerId) {
        this.viewedTips = new HashSet<>();
        historyFile = new File(HISTORY_PATH.toString(), viewerId + ".history");
        loadHistory();
    }

    public void addTip(ResourceLocation tipId) {
        viewedTips.add(tipId);
    }

    public boolean hasViewed(ResourceLocation tipId) {
        return viewedTips.contains(tipId);
    }

    public Set<ResourceLocation> getViewedTips() {
        return viewedTips;
    }

    public void saveHistory() {
        historyFile.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile))) {
            for (ResourceLocation tip : viewedTips) {
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
                    viewedTips.add(new ResourceLocation(line));
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

package fr.foxelia.igtips.data;

import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.datapack.TipRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static fr.foxelia.igtips.data.HistoryManager.HISTORY_PATH;

public class TipHistory {

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

    @Nullable
    public Identifier getRandomTip(boolean use) {
        Identifier tip = TipRegistry.getRandomTip(getViewedTips());
        if (tip == null && CommonConfig.isRecyclingTips()) {
            viewedTips.clear();
            tip = TipRegistry.getRandomTip(getViewedTips());
        }
        if(tip != null && use) {
            addTip(tip);
            saveHistory();
        }
        return tip;
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
}

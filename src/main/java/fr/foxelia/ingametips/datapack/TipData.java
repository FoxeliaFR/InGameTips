package fr.foxelia.ingametips.datapack;

import java.util.Map;

public class TipData {
    private int displayTime = -1;
    private Map<String, String> translations;

    public int getDisplayTime() {
        return displayTime;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    @Override
    public String toString() {
        return "TipData{" +
                "displayTime=" + displayTime +
                ", translations=" + translations +
                '}';
    }
}
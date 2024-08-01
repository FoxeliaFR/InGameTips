package fr.foxelia.ingametips.datapack;

import fr.foxelia.ingametips.client.TranslatableTip;

import java.util.HashMap;
import java.util.Map;

public class TipRegistry {

    private static final Map<String, TranslatableTip> TIPS = new HashMap<>();

    public static void registerTip(String datapackName, String fileName, TranslatableTip tip) {
        String key = datapackName + ":" + fileName;
        TIPS.put(key, tip);
    }

    public static TranslatableTip getTip(String key) {
        return TIPS.get(key);
    }

    public static Map<String, TranslatableTip> getAllTips() {
        return TIPS;
    }

    protected static void reset() {
        TIPS.clear();
    }
}

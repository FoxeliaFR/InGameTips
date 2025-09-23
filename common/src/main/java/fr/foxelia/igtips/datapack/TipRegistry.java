package fr.foxelia.igtips.datapack;

import fr.foxelia.igtips.data.TipHistory;
import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TipRegistry {

    private static final Map<Identifier, TranslatableTip> TIPS = new HashMap<>();

    public static void registerTip(String datapackName, String fileName, TranslatableTip tip) {
        registerTip(new Identifier(datapackName, fileName), tip);
    }

    public static void registerTip(Identifier key, TranslatableTip tip) {
        TIPS.put(key, tip);
    }

    @Nullable
    public static TranslatableTip getTip(String key) {
        Identifier identifier = Identifier.tryParse(key);
        if(identifier == null) return null;
        return TIPS.get(identifier);
    }

    @Nullable
    public static TranslatableTip getTip(Identifier key) {
        if(!TIPS.containsKey(key)) {
            return null;
        }
        return TIPS.get(key);
    }

    @Nullable
    public static Identifier getRandomTip() {
        return getRandomTip(Collections.emptySet());
    }

    @Nullable
    public static Identifier getRandomTip(Set<Identifier> excludedTips) {
        if(TIPS.isEmpty()) return null;
        Set<Identifier> availableTips = new HashSet<>(TIPS.keySet());
        availableTips.removeAll(excludedTips);
        if(!availableTips.isEmpty()) {
            return (Identifier) availableTips.toArray()[new Random().nextInt(availableTips.size())];
        }
        return null;
    }

    public static Map<Identifier, TranslatableTip> getAllTips() {
        return TIPS;
    }

    protected static void reset() {
        TIPS.clear();
    }
}

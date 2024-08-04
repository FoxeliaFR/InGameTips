package fr.foxelia.ingametips.datapack;

import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TipRegistry {

    private static final Map<ResourceLocation, TranslatableTip> TIPS = new HashMap<>();

    public static void registerTip(String datapackName, String fileName, TranslatableTip tip) {
        registerTip(new ResourceLocation(datapackName, fileName), tip);
    }

    public static void registerTip(ResourceLocation key, TranslatableTip tip) {
        TIPS.put(key, tip);
    }

    @Nullable
    public static TranslatableTip getTip(String key) {
        ResourceLocation resourceLocation = ResourceLocation.tryParse(key);
        if(resourceLocation == null) return null;
        return TIPS.get(resourceLocation);
    }

    @Nullable
    public static TranslatableTip getTip(ResourceLocation key) {
        if(!TIPS.containsKey(key)) {
            return null;
        }
        return TIPS.get(key);
    }

    public static Map<ResourceLocation, TranslatableTip> getAllTips() {
        return TIPS;
    }

    protected static void reset() {
        TIPS.clear();
    }
}

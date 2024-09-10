package fr.foxelia.igtips.datapack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.InGameTipsCommonConfigs;
import fr.foxelia.igtips.tip.TranslatableTip;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class TipLoader extends JsonDataLoader {
    private static final Gson GSON = new Gson();

    public TipLoader() {
        super(GSON, "tips");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> objectIn, ResourceManager resourceManagerIn, Profiler profilerIn) {
        int tipCount = 0;
        TipRegistry.reset();
        for (Map.Entry<Identifier, JsonElement> entry : objectIn.entrySet()) {
            if(InGameTipsCommonConfigs.disabledNamespaces.get().contains(entry.getKey().getNamespace())) {
                continue;
            }
            try {
                TipData tipData = GSON.fromJson(entry.getValue(), TipData.class);
                TranslatableTip tip;
                if(tipData.getTranslations().containsKey("en_us")) {
                    tip = new TranslatableTip("en_us");
                } else {
                    tip = new TranslatableTip(tipData.getTranslations().keySet().iterator().next());
                }
                tipData.getTranslations().forEach(tip::addTranslation);
                if (tipData.getDisplayTime() > 0) {
                    tip.setDisplayTime(tipData.getDisplayTime());
                } else {
                    tip.setDisplayTime(-1);
                }
                TipRegistry.registerTip(entry.getKey(), tip);
                tipCount++;
            } catch (JsonSyntaxException e) {
                InGameTips.LOGGER.error("Error parsing JSON for tip {}: {}", entry.getKey(), e.getMessage());
            }
        }
        InGameTips.LOGGER.info("Loaded {} tips", tipCount);
    }
}
package fr.foxelia.ingametips.datapack;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import fr.foxelia.ingametips.tip.TranslatableTip;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import static fr.foxelia.ingametips.InGameTips.LOGGER;

import java.util.Map;

public class TipLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();

    public TipLoader() {
        super(GSON, "tips");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        int tipCount = 0;
        TipRegistry.reset();
        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
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
                TipRegistry.registerTip(entry.getKey().getNamespace(), entry.getKey().getPath(), tip);
                tipCount++;
            } catch (JsonSyntaxException e) {
                LOGGER.error("Error parsing JSON for tip {}: {}", entry.getKey(), e.getMessage());
            }
        }
        LOGGER.info("Initialized {} tips.", tipCount);
    }
    public static void register(IEventBus eventBus) {
        eventBus.addListener(TipLoader::onAddReloadListener);
    }

    private static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new TipLoader());
    }
}
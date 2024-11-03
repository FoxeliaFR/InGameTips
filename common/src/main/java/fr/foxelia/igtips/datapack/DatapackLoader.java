package fr.foxelia.igtips.datapack;

import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resource.ResourceType;

public class DatapackLoader {

    public static void register() {
        // Register the datapack loader
        ReloadListenerRegistry.register(ResourceType.SERVER_DATA, new TipLoader());
    }
}

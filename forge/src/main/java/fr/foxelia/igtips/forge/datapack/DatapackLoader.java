package fr.foxelia.igtips.forge.datapack;

import fr.foxelia.igtips.datapack.TipLoader;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class DatapackLoader {

    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new TipLoader());
    }

}

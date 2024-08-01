package fr.foxelia.ingametips.overlay;

import fr.foxelia.ingametips.InGameTips;
import fr.foxelia.ingametips.overlay.PopUpOverlay;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InGameTips.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OverlayRegistry {

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        new PopUpManager();
        event.registerAboveAll(InGameTips.MOD_ID + "_popup", new PopUpOverlay());
    }
}

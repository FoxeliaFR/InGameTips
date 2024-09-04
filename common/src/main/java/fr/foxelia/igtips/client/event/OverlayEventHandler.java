package fr.foxelia.igtips.client.event;

import dev.architectury.event.events.client.ClientGuiEvent;
import fr.foxelia.igtips.client.overlay.PopUpManager;
import fr.foxelia.igtips.client.overlay.PopUpRenderer;
import net.minecraft.client.gui.DrawContext;

public class OverlayEventHandler {

    private static PopUpRenderer renderer;

    public static void register() {
        PopUpManager.register();
        renderer = new PopUpRenderer();
        ClientGuiEvent.RENDER_HUD.register(OverlayEventHandler::onHudRender);
    }

    private static void onHudRender(DrawContext drawContext, float deltaTick) {
        renderer.render(drawContext);
    }


}

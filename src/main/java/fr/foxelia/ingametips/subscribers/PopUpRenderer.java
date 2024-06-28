package fr.foxelia.ingametips.subscribers;

import fr.foxelia.ingametips.PopUpManager;
import fr.foxelia.ingametips.TipAnimation;
import fr.foxelia.ingametips.client.PopUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class PopUpRenderer {

    private static final int OFFSET = 4;
    private Minecraft mc = Minecraft.getInstance();
    private PopUpManager manager = PopUpManager.getInstance();

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGuiOverlayEvent event) {
        // Check if player is in F1
        if(mc.noRender) return;

        PopUp popUp = manager.getQueued();
        if(popUp == null) return;
        TipAnimation animation = manager.getAnimation();

        GuiGraphics guiGraphics = event.getGuiGraphics();

        Font fontRenderer = mc.font;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int popUpStart = (screenWidth - 256) / 2;

        int y = animation.getY();

        manager.getAnimation().playSound();

        // Draw background
        guiGraphics.blit(popUp.getBackground(), popUpStart, y, 0, animation.getBlitOffset(), 256, animation.getBackgroundHeight());

        // Draw text
        y += 3;
        guiGraphics.drawCenteredString(fontRenderer, I18n.get("tip.igtips.name"), screenWidth / 2, y, 0xFFFFFF);
        for(FormattedCharSequence line : animation.getLines()) {
            y += fontRenderer.lineHeight + 2;
            guiGraphics.drawString(fontRenderer, line, popUpStart + 5, y, 0xFFFFFF);
        }

    }

}

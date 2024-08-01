package fr.foxelia.ingametips.overlay;

import fr.foxelia.ingametips.client.PopUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class PopUpOverlay implements IGuiOverlay {

    private final Minecraft mc = Minecraft.getInstance();
    private final PopUpManager manager = PopUpManager.getInstance();

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        // Check if player is in F1
        if (mc.noRender) return;

        PopUp popUp = manager.getQueued();
        if (popUp == null) return;
        TipAnimation animation = manager.getAnimation();

        int popUpStart = (screenWidth - 256) / 2;
        int y = animation.getY();

        manager.getAnimation().playSound();

        // Draw background
        guiGraphics.blit(popUp.getBackground(), popUpStart, y, 0, animation.getBlitOffset(), 256, animation.getBackgroundHeight());

        // Draw text
        y += 3;
        guiGraphics.drawCenteredString(mc.font, I18n.get("tip.igtips.name"), screenWidth / 2, y, 0xFFFFFF);
        for (FormattedCharSequence line : animation.getLines()) {
            y += mc.font.lineHeight + 2;
            guiGraphics.drawString(mc.font, line, popUpStart + 5, y, 0xFFFFFF);
        }
    }
}

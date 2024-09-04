package fr.foxelia.igtips.client.overlay;

import fr.foxelia.igtips.tip.PopUp;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;

public class PopUpRenderer {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final PopUpManager manager = PopUpManager.getInstance();

    public void render(DrawContext guiGraphics) {
        // Check if player is in F1
        if (mc.skipGameRender) return;

        PopUp popUp = manager.getQueued();
        if (popUp == null) return;
        PopUpAnimation animation = manager.getAnimation();

        int popUpStart = (getScreenWidth() - 256) / 2;
        int y = animation.getY();

        manager.getAnimation().verifySoundEvent();

        // Top texture with the title
        Identifier background = popUp.getBackground();
        guiGraphics.drawTexture(background, popUpStart, y, 0, PopUp.TexturePart.TOP.offset(), 256, PopUp.TexturePart.TOP.height());
        y += 4;
        guiGraphics.drawCenteredTextWithShadow(mc.textRenderer, I18n.translate("tip.igtips.name"), getScreenWidth() / 2, y, 0xFFFFFF);

        // Middle and bottom textures with the message
        OrderedText lastLine = animation.getLines().get(animation.getLines().size() - 1);
        for (OrderedText line : animation.getLines()) {
            y += mc.textRenderer.fontHeight;
            if(line.equals(lastLine)) {
                guiGraphics.drawTexture(background, popUpStart, y, 0, PopUp.TexturePart.BOTTOM.offset(), 256, PopUp.TexturePart.BOTTOM.height());
            } else guiGraphics.drawTexture(background, popUpStart, y, 0, PopUp.TexturePart.MIDDLE.offset(), 256, PopUp.TexturePart.MIDDLE.height());
            y += 2;
            guiGraphics.drawTextWithShadow(mc.textRenderer, line, popUpStart + 5, y, 0xFFFFFF);
        }
    }

    private int getScreenWidth() {
        return mc.getWindow().getScaledWidth();
    }

}

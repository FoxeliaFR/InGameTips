package fr.foxelia.ingametips.tip;

import net.minecraft.resources.ResourceLocation;

public interface ITip {

    int DEFAULT_DISPLAY_TIME = 5000;

    String getMessage();

    default ResourceLocation getBackground() {
        return new ResourceLocation("igtips", "textures/gui/tip.png");
    }

    default int getDisplayTime() {
        return DEFAULT_DISPLAY_TIME;
    }
}

package fr.foxelia.ingametips.tip;

import net.minecraft.resources.ResourceLocation;

public interface ITip {

    int DEFAULT_DISPLAY_TIME = 5000;

    String message();

    default ResourceLocation getBackground() {
        return new ResourceLocation("igtips", "textures/gui/popup.png");
    }

    default int displayTime() {
        return DEFAULT_DISPLAY_TIME;
    }
}

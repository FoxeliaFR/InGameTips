package fr.foxelia.ingametips.client;

import net.minecraft.resources.ResourceLocation;

public interface ITip {

    String getMessage();

    default ResourceLocation getBackground() {
        return new ResourceLocation("igtips", "textures/gui/tip.png");
    }

    default int getDisplayTime() {
        return 5000;
    }
}

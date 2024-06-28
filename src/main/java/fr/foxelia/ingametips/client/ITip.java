package fr.foxelia.ingametips.client;

import net.minecraft.resources.ResourceLocation;

public interface ITip {

    String getTip();

    default ResourceLocation getBackground() {
        return new ResourceLocation("igtips", "textures/gui/tip.png");
    }


}

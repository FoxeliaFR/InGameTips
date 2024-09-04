package fr.foxelia.igtips.tip;

import fr.foxelia.igtips.InGameTips;
import net.minecraft.util.Identifier;

public interface ITip {

    int DEFAULT_DISPLAY_TIME = 5000;

    String message();

    default Identifier getBackground() {
        return new Identifier(InGameTips.MOD_ID, "textures/gui/popup.png");
    }

    default int displayTime() {
        return DEFAULT_DISPLAY_TIME;
    }
}

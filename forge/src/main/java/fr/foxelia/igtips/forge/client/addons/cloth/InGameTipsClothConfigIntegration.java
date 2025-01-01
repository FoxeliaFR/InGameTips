package fr.foxelia.igtips.forge.client.addons.cloth;

import dev.architectury.platform.Platform;
import fr.foxelia.igtips.client.addons.clothconfig.InGameTipsMainConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class InGameTipsClothConfigIntegration {

    public static void registerConfigScreen() {
        if(Platform.getOptionalMod("cloth_config").isPresent()) {
            ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (mc, parent) -> new InGameTipsMainConfigScreen(parent)
                    )
            );
        }
    }
}

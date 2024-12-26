package fr.foxelia.igtips.client.addons.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.architectury.platform.Platform;
import fr.foxelia.igtips.client.addons.clothconfig.InGameTipsMainConfigScreen;

public class InGameTipsModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if(Platform.getOptionalMod("cloth-config2").isPresent()) {
                return new InGameTipsMainConfigScreen(parent);
            } else {
                return null;
            }
        };
    }
}

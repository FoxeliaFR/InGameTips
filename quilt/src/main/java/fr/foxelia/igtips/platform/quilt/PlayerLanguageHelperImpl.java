package fr.foxelia.igtips.platform.quilt;

import fr.foxelia.igtips.accessor.PlayerLanguageAccessor;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerLanguageHelperImpl {

    public static String getPlayerLanguage(ServerPlayerEntity player) {
        if(player instanceof PlayerLanguageAccessor access) {
            return access.getLanguage();
        } else return "en_us";
    }

}

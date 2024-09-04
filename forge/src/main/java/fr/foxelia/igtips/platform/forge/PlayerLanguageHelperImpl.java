package fr.foxelia.igtips.platform.forge;

import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerLanguageHelperImpl {

    public static String getPlayerLanguage(ServerPlayerEntity player) {
        return player.getLanguage();
    }

}

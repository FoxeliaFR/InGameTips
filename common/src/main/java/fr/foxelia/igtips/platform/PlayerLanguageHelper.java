package fr.foxelia.igtips.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerLanguageHelper {

    @ExpectPlatform
    public static String getPlayerLanguage(ServerPlayerEntity player) {
        throw new AssertionError();
    }

}

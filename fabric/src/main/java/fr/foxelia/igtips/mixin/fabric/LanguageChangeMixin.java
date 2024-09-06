package fr.foxelia.igtips.mixin.fabric;

import fr.foxelia.igtips.network.fabric.PlayerLanguagePacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(I18n.class)
public class LanguageChangeMixin {

    @Inject(method = "setLanguage", at = @At("RETURN"))
    private static void onLanguageChange(Language language, CallbackInfo ci) {
        // Envoyer un paquet au serveur avec la nouvelle langue du joueur
        MinecraftClient client = MinecraftClient.getInstance();

        System.out.println(language); // Debug
        System.out.println(ci); // Debug
        if (client.getNetworkHandler() != null) {
            // Créer et envoyer un paquet pour notifier le serveur

            //PlayerLanguagePacket.sendLanguageToServer();
        }
    }
}

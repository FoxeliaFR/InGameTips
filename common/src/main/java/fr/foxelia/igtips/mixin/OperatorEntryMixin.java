package fr.foxelia.igtips.mixin;

import com.mojang.authlib.GameProfile;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.CommonConfigManager;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OperatorEntry.class)
public class OperatorEntryMixin {

    @Inject(method = "<init>(Lcom/mojang/authlib/GameProfile;IZ)V", at = @At("TAIL"))
    private void onConstruct(GameProfile profile, int permissionLevel, boolean bypassPlayerLimit, CallbackInfo ci) {
        // Check if the player is connected to the server
        if(InGameTips.SERVER != null) {
            ServerPlayerEntity player = InGameTips.SERVER.getPlayerManager().getPlayer(profile.getId());
            if(player != null) {
                if(permissionLevel >= CommonConfigManager.EDIT_PERMISSION_LEVEL) {
                    // Sync the config to the player if the player meets the required level
                    CommonConfigManager.syncConfigToPlayer(player, true);
                } else {
                    // Unsync the config from the player if the level is lower than the required level
                    CommonConfigManager.unsyncConfigFromPlayer(player);
                }
            }
        }
    }

}

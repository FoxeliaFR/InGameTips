package fr.foxelia.igtips.mixin;

import com.mojang.authlib.GameProfile;
import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.config.CommonConfigManager;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "removeFromOperators", at = @At("TAIL"))
    public void onOperatorRemove(GameProfile profile, CallbackInfo ci) {
        // This method is called when operators are removed from the server
        if(InGameTips.SERVER != null) {
            ServerPlayerEntity player = InGameTips.SERVER.getPlayerManager().getPlayer(profile.getId());
            if(player != null) CommonConfigManager.unsyncConfigFromPlayer(player);
        }
    }

}

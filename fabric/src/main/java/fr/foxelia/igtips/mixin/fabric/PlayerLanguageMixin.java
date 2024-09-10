package fr.foxelia.igtips.mixin.fabric;

import fr.foxelia.igtips.accessor.fabric.PlayerLanguageAccessor;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerLanguageMixin implements PlayerLanguageAccessor {
    @Unique
    private static final TrackedData<String> LANGUAGE = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.STRING);

    // Initialisation du DataTracker
    @Inject(method = "initDataTracker", at = @At("RETURN"))
    private void initLanguageTracker(CallbackInfo ci) {
        DataTracker dataTracker = ((PlayerEntity) (Object) this).getDataTracker();
        dataTracker.startTracking(LANGUAGE, "en_us");
    }

    @Override
    public String getLanguage() {
        return ((PlayerEntity) (Object) this).getDataTracker().get(LANGUAGE);
    }

    @Override
    public void setLanguage(String language) {
        ((PlayerEntity) (Object) this).getDataTracker().set(LANGUAGE, language);
    }
}

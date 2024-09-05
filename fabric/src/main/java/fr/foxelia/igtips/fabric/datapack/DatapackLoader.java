package fr.foxelia.igtips.fabric.datapack;

import fr.foxelia.igtips.InGameTips;
import fr.foxelia.igtips.datapack.TipLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.util.Identifier;

public class DatapackLoader extends TipLoader implements IdentifiableResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return new Identifier(InGameTips.MOD_ID, "tip_loader");
    }
}

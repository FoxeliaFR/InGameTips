package fr.foxelia.igtips.client.addons.clothconfig;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

@Config(name = MOD_ID)
public class InGameTipsClothCommonConfigScreen {

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.of("Test"));

        // Categories
        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));
        general.addEntry(builder.entryBuilder().startBooleanToggle(Text.of("Test"), false).build());

        // Return the screen here with the one you created from Cloth Config Builder
        return builder.build();
    }

}
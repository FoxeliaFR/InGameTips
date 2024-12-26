package fr.foxelia.igtips.client.addons.clothconfig;

import fr.foxelia.igtips.client.config.ClientConfig;
import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static fr.foxelia.igtips.InGameTips.MOD_ID;

@Config(name = MOD_ID)
public class InGameTipsClothClientConfigScreen {

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.igtips.client.title"));

        // Categories
        ConfigCategory clientSettings = builder.getOrCreateCategory(Text.translatable("config.igtips.main.button.client"));

        // Get default values from the config
        IClientInGameTipsConfig defaultConfig = new ClientConfig.DefaultClientConfig();

        // Entries
        clientSettings.addEntry(builder.entryBuilder()
                .startBooleanToggle(Text.translatable("config.igtips.client.option.tips_enabled"), ClientConfig.isTipsEnabled())
                .setTooltip(Text.translatable("config.igtips.client.option.tips_enabled.tooltip"))
                .setDefaultValue(defaultConfig.isTipsEnabled())
                .setSaveConsumer(newValue -> ClientConfig.setTipsEnabled(newValue))
                .build());

        clientSettings.addEntry(builder.entryBuilder()
                .startBooleanToggle(Text.translatable("config.igtips.client.option.sound_enabled"), ClientConfig.isSoundEnabled())
                .setTooltip(Text.translatable("config.igtips.client.option.sound_enabled.tooltip"))
                .setDefaultValue(defaultConfig.isSoundEnabled())
                .setSaveConsumer(newValue -> ClientConfig.setSoundEnabled(newValue))
                .build());

        clientSettings.addEntry(builder.entryBuilder()
                .startIntField(Text.translatable("config.igtips.client.option.max_lines"), ClientConfig.getMaxLines())
                .setTooltip(Text.translatable("config.igtips.client.option.max_lines.tooltip"))
                .setDefaultValue(defaultConfig.getMaxLines())
                .setMin(1)
                .setMax(64)
                .setSaveConsumer(newValue -> ClientConfig.setMaxLines(newValue))
                .build());

        // Return the screen here with the one you created from Cloth Config Builder
        return builder.build();
    }

}
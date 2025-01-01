package fr.foxelia.igtips.client.addons.clothconfig;

import fr.foxelia.igtips.client.config.ClientConfig;
import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.config.CommonConfigManager;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
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
                .setParentScreen(parent);

        boolean isServerConfig = false;
        if(CommonConfigManager.SERVER_CONFIG != null) {
            isServerConfig = true;
            builder.setTitle(Text.translatable("config.igtips.common.title.server"));
        } else {
            builder.setTitle(Text.translatable("config.igtips.common.title.local"));
        }

        // Categories
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.igtips.common.category.general"));

        // Get default values from the config
        ICommonInGameTipsConfig defaultConfig = new CommonConfig.DefaultCommonConfig();

        // Entries
        Integer scheduleTime;
        general.addEntry(builder.entryBuilder()
                        .startIntField(Text.translatable("config.igtips.common.option.schedule_time"), CommonConfig.getScheduleInterval())
                        .setTooltip(Text.translatable("config.igtips.common.option.schedule_time.tooltip"))
                        .setDefaultValue(defaultConfig.getScheduleInterval())
                        .setSaveConsumer(newValue -> System.out.println("Test"))
                        .build());

                /*.startBooleanToggle(Text.translatable("config.igtips.client.option.tips_enabled"), ClientConfig.isTipsEnabled())
                .setTooltip(Text.translatable("config.igtips.client.option.tips_enabled.tooltip"))
                .setDefaultValue(defaultConfig.isTipsEnabled())
                .setSaveConsumer(newValue -> ClientConfig.setTipsEnabled(newValue))
                .build());*/

        // Return the screen here with the one you created from Cloth Config Builder
        return builder.build();
    }

}
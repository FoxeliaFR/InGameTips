package fr.foxelia.igtips.client.addons.clothconfig;

import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.config.CommonConfigManager;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import fr.foxelia.igtips.network.ConfigPacket;
import fr.foxelia.igtips.network.NetworkHandler;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Optional;

import static fr.foxelia.igtips.client.addons.clothconfig.InGameTipsMainConfigScreen.isLocal;

public class InGameTipsClothCommonConfigScreen {

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent);

        boolean isRemote;
        if(CommonConfigManager.SERVER_CONFIG != null && !isLocal()) {
            isRemote = true;
            builder.setTitle(Text.translatable("config.igtips.common.title.server"));
        } else {
            isRemote = false;
            builder.setTitle(Text.translatable("config.igtips.common.title.local"));
        }

        // Categories
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.igtips.common.category.general"));
        ConfigCategory sync = builder.getOrCreateCategory(Text.translatable("config.igtips.common.category.sync"));

        // Get default values from the config
        ICommonInGameTipsConfig defaultConfig = new CommonConfig.DefaultCommonConfig();

        // General Entries
        IntegerListEntry scheduleTime = builder.entryBuilder()
                .startIntField(Text.translatable("config.igtips.common.option.schedule_time"),
                        isRemote ? CommonConfigManager.SERVER_CONFIG.getScheduleInterval() : CommonConfig.getScheduleInterval())
                .setTooltip(Text.translatable("config.igtips.common.option.schedule_time.tooltip"))
                .setDefaultValue(defaultConfig.getScheduleInterval())
                .setMin(0)
                .build();
        general.addEntry(scheduleTime);

        StringListListEntry disabledNamespaces = builder.entryBuilder()
                .startStrList(Text.translatable("config.igtips.common.option.disabled_namespaces"),
                        isRemote ? CommonConfigManager.SERVER_CONFIG.getDisabledNamespaces() : CommonConfig.getDisabledNamespaces())
                .setTooltip(Text.translatable("config.igtips.common.option.disabled_namespaces.tooltip"))
                .setDefaultValue(defaultConfig.getDisabledNamespaces())
                .setErrorSupplier(value -> {
                    for(String namespace : value) {
                        if(namespace.isEmpty()) {
                            return Optional.of(Text.translatable("config.igtips.common.option.disabled_namespaces.error.empty"));
                        }
                        if(!namespace.matches("^[a-z0-9._-]+$")) {
                            return Optional.of(Text.translatable("config.igtips.common.option.disabled_namespaces.error.invalid"));
                        }
                    }
                    return Optional.empty();
                })
                .build();
        general.addEntry(disabledNamespaces);

        BooleanListEntry tipRecycling = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("config.igtips.common.option.tip_recycling"),
                        isRemote ? CommonConfigManager.SERVER_CONFIG.isRecyclingTips() : CommonConfig.isRecyclingTips())
                .setTooltip(Text.translatable("config.igtips.common.option.tip_recycling.tooltip"))
                .setDefaultValue(defaultConfig.isRecyclingTips())
                .build();
        general.addEntry(tipRecycling);

        // Synchronization Entries
        BooleanListEntry syncSending = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("config.igtips.common.option.sync_sending"),
                        isRemote ? CommonConfigManager.SERVER_CONFIG.isSyncSending() : CommonConfig.isSyncSending())
                .setTooltip(Text.translatable("config.igtips.common.option.sync_sending.tooltip"))
                .setDefaultValue(defaultConfig.isSyncSending())
                .build();
        sync.addEntry(syncSending);

        BooleanListEntry individualTips = builder.entryBuilder()
                .startBooleanToggle(Text.translatable("config.igtips.common.option.individual_tips"),
                        isRemote ? CommonConfigManager.SERVER_CONFIG.isIndividualTips() : CommonConfig.isIndividualTips())
                .setTooltip(Text.translatable("config.igtips.common.option.individual_tips.tooltip"))
                .setDefaultValue(defaultConfig.isIndividualTips())
                .setRequirement(() -> syncSending.getValue())
                .build();
        sync.addEntry(individualTips);

        // Save the config
        builder.setSavingRunnable(() -> {
            if(isRemote) {
                ConfigPacket packet = new ConfigPacket(
                        scheduleTime.getValue(),
                        disabledNamespaces.getValue(),
                        syncSending.getValue(),
                        individualTips.getValue(),
                        tipRecycling.getValue(),
                        false
                );
                NetworkHandler.CHANNEL.sendToServer(packet);
            } else {
                CommonConfig.setScheduleInterval(scheduleTime.getValue());
                CommonConfig.setDisabledNamespaces(disabledNamespaces.getValue());
                CommonConfig.setRecyclingTips(tipRecycling.getValue());
                CommonConfig.setSyncSending(syncSending.getValue());
                CommonConfig.setIndividualTips(individualTips.getValue());
            }
        });

        // Return the screen here with the one you created from Cloth Config Builder
        return builder.build();
    }

}
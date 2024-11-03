package fr.foxelia.igtips.quilt.config;

import fr.foxelia.igtips.config.CommonConfig;
import fr.foxelia.igtips.config.ICommonInGameTipsConfig;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueList;

import java.util.List;

public class QuiltCommonConfig extends ReflectiveConfig implements ICommonInGameTipsConfig{

    @Comment("Common configuration settings for InGameTips")
    @SerializedName("common")
    private final CommonSection commonSection = new CommonSection();

    private class CommonSection extends Section {
        @Comment("The time in seconds between each tip. Set to 0 to disable scheduled tips.\nExample: 60 (1 minute), 90 (1 minute 30 seconds), 300 (5 minutes)")
        @IntegerRange(min = 0, max = Integer.MAX_VALUE)
        @SerializedName("schedule_time")
        private final TrackedValue<Integer> scheduleTime = value(CommonConfig.getScheduleInterval());
        @Comment("List of mod namespaces to disable tips from.\nExample: [\"modid\", \"datapackid\"]")
        @SerializedName("disabled_namespaces")
        private final TrackedValue<ValueList<String>> disabledNamespaces = list("", CommonConfig.getDisabledNamespaces().toArray(new String[0]));
        @Comment("If enabled, tips will be recycled when all tips have been sent.")
        @SerializedName("tip_recycling")
        private final TrackedValue<Boolean> tipRecycling = value(CommonConfig.isRecyclingTips());
    }

    @Comment("Synchronized sending")
    @SerializedName("sync_sending")
    private final SyncSection syncSection = new SyncSection();

    private class SyncSection extends Section {
        @Comment("If enabled, tips will be sent to all players at the same time.")
        @SerializedName("sync_sending")
        private final TrackedValue<Boolean> syncSending = value(CommonConfig.isSyncSending());
        @Comment("If enabled, each player will receive a different tip. This option is only available if synchronized sendings is enabled.")
        @SerializedName("individual_tips")
        private final TrackedValue<Boolean> individualTips = value(CommonConfig.isIndividualTips());
    }


    @Override
    public int getScheduleInterval() {
        return commonSection.scheduleTime.value();
    }

    @Override
    public List<String> getDisabledNamespaces() {
        return commonSection.disabledNamespaces.value();
    }

    @Override
    public boolean getSyncSending() {
        return syncSection.syncSending.value();
    }

    @Override
    public boolean isIndividualTips() {
        return syncSection.individualTips.value();
    }

    @Override
    public boolean isRecyclingTips() {
        return commonSection.tipRecycling.value();
    }
}

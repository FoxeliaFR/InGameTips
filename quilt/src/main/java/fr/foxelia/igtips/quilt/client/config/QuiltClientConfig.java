package fr.foxelia.igtips.quilt.client.config;

import fr.foxelia.igtips.client.config.ClientConfig;
import fr.foxelia.igtips.client.config.IClientInGameTipsConfig;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;

public class QuiltClientConfig extends ReflectiveConfig implements IClientInGameTipsConfig {


    @Comment("Client configuration settings for InGameTips")
    @SerializedName("client")
    private final ClientSection clientSection = new ClientSection();

    private static class ClientSection extends Section {

        @Comment("Enable or disable the display of tips in-game")
        @SerializedName("enable_tips")
        private final TrackedValue<Boolean> enableTips = value(ClientConfig.isTipsEnabled());

        @Comment("Enable or disable the sound when a tip is displayed")
        @SerializedName("enable_sound")
        private final TrackedValue<Boolean> enableSound = value(ClientConfig.isSoundEnabled());
        @Comment("Maximum number of lines displayed in a tip")
        @IntegerRange(min = 1, max = 64)
        @SerializedName("max_lines")
        private final TrackedValue<Integer> maxLines = value(ClientConfig.getMaxLines());
    }

    /*
     * Getters
     */

    @Override
    public boolean isTipsEnabled() {
        return clientSection.enableTips.value();
    }

    @Override
    public boolean isSoundEnabled() {
        return clientSection.enableSound.value();
    }

    @Override
    public int getMaxLines() {
        return clientSection.maxLines.value();
    }

    /*
     * Setters
     */

    @Override
    public void setTipsEnabled(boolean enabled) {
        clientSection.enableTips.setValue(enabled);
    }

    @Override
    public void setSoundEnabled(boolean enabled) {
        clientSection.enableSound.setValue(enabled);
    }

    @Override
    public void setMaxLines(int maxLines) {
        clientSection.maxLines.setValue(maxLines);
    }
}

package fr.foxelia.igtips.client.config;

import fr.foxelia.igtips.platform.ConfigSetupHelper;

public class ClientConfig {

    private static IClientInGameTipsConfig config = new DefaultClientConfig();

    public static void setupClientConfig() {
        config = ConfigSetupHelper.setupClientConfig();
    }

    public static boolean isTipsEnabled() {
        return config.isTipsEnabled();
    }

    public static boolean isSoundEnabled() {
        return config.isSoundEnabled();
    }

    public static int getMaxLines() {
        return config.getMaxLines();
    }

    public static void setTipsEnabled(boolean enabled) {
        config.setTipsEnabled(enabled);
    }

    public static void setSoundEnabled(boolean enabled) {
        config.setSoundEnabled(enabled);
    }

    public static void setMaxLines(int maxLines) {
        config.setMaxLines(maxLines);
    }

    public static class DefaultClientConfig implements IClientInGameTipsConfig {

        @Override
        public boolean isTipsEnabled() {
            return true;
        }

        @Override
        public boolean isSoundEnabled() {
            return true;
        }

        @Override
        public int getMaxLines() {
            return 9;
        }

    }
}





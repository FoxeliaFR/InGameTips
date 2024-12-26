package fr.foxelia.igtips.config;

import fr.foxelia.igtips.platform.ConfigSetupHelper;

import java.util.ArrayList;
import java.util.List;

public class CommonConfig {

    private static ICommonInGameTipsConfig config = new DefaultCommonConfig();

    public static void setupCommonConfig() {
        config = ConfigSetupHelper.setupCommonConfig();
    }

    public static int getScheduleInterval() {
        return config.getScheduleInterval();
    }

    public static List<String> getDisabledNamespaces() {
        return config.getDisabledNamespaces();
    }

    public static boolean isSyncSending() {
        return config.getSyncSending();
    }

    public static boolean isIndividualTips() {
        return config.isIndividualTips();
    }

    public static boolean isRecyclingTips() {
        return config.isRecyclingTips();
    }

    public static void setScheduleInterval(int interval) {
        config.setScheduleInterval(interval);
    }

    public static void setDisabledNamespaces(List<String> namespaces) {
        config.setDisabledNamespaces(namespaces);
    }

    public static void setSyncSending(boolean syncSending) {
        config.setSyncSending(syncSending);
    }

    public static void setIndividualTips(boolean individualTips) {
        config.setIndividualTips(individualTips);
    }

    public static void setRecyclingTips(boolean recyclingTips) {
        config.setRecyclingTips(recyclingTips);
    }

    public static class DefaultCommonConfig implements ICommonInGameTipsConfig {

        @Override
        public int getScheduleInterval() {
            return 900;
        }

        @Override
        public List<String> getDisabledNamespaces() {
            return new ArrayList<>();
        }

        @Override
        public boolean getSyncSending() {
            return false;
        }

        @Override
        public boolean isIndividualTips() {
            return false;
        }

        @Override
        public boolean isRecyclingTips() {
            return false;
        }

    }

}



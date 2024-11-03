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

}

class DefaultCommonConfig implements ICommonInGameTipsConfig {

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



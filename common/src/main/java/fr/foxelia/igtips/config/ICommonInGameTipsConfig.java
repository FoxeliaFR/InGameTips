package fr.foxelia.igtips.config;

import java.util.List;

public interface ICommonInGameTipsConfig {

    int getScheduleInterval();
    List<String> getDisabledNamespaces();
    boolean getSyncSending();
    boolean isIndividualTips();
    boolean isRecyclingTips();



}

package fr.foxelia.igtips.config;

import java.util.List;

public interface ICommonInGameTipsConfig {

    default void setScheduleInterval(int scheduleInterval) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    default void setDisabledNamespaces(List<String> disabledNamespaces) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    default void setSyncSending(boolean syncSending) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    default void setIndividualTips(boolean individualTips) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    default void setRecyclingTips(boolean recyclingTips) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    int getScheduleInterval();
    List<String> getDisabledNamespaces();
    boolean isSyncSending();
    boolean isIndividualTips();
    boolean isRecyclingTips();



}

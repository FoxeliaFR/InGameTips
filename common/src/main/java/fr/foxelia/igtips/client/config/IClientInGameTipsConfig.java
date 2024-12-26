package fr.foxelia.igtips.client.config;

public interface IClientInGameTipsConfig {

    default void setTipsEnabled(boolean enabled) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    default void setSoundEnabled(boolean enabled) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    default void setMaxLines(int maxLines) {
        throw new UnsupportedOperationException("Cannot modify config values in a read-only config");
    }

    boolean isTipsEnabled();
    boolean isSoundEnabled();
    int getMaxLines();

}

package fr.foxelia.ingametips.client;

import java.util.HashMap;
import java.util.Map;

public class TranslatableTip implements ITip {

    private final String DEFAULT_LANGUAGE;
    /*
     * Map of translations for the tip
     * Key: language code
     * Value: translated tip
     */
    private final Map<String, String> translations = new HashMap<>();
    private int displayTime = -1;

    public TranslatableTip(String defaultLanguage) {
        this.DEFAULT_LANGUAGE = defaultLanguage;
    }

    public void addTranslation(String languageCode, String translation) {
        translations.put(languageCode, translation);
    }

    @Override
    public String getMessage() {
        return getTipFromLang(DEFAULT_LANGUAGE);
    }

    public String getTipFromLang(String languageCode) {
        return translations.getOrDefault(languageCode, translations.get(DEFAULT_LANGUAGE));
    }

    @Override
    public int getDisplayTime() {
        if (displayTime == -1) return DEFAULT_DISPLAY_TIME;
        return displayTime;
    }

    public void setDisplayTime(int displayTime) {
        this.displayTime = displayTime;
    }

    public BasicTip toBasicTip() {
        return toBasicTip(DEFAULT_LANGUAGE);
    }

    public BasicTip toBasicTip(String languageCode) {
        return new BasicTip(getTipFromLang(languageCode), getDisplayTime());
    }
}

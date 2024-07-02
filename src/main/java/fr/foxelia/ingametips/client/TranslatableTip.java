package fr.foxelia.ingametips.client;

import java.util.HashMap;
import java.util.Map;

public class TranslatableTip implements ITip {

    private static final String DEFAULT_LANGUAGE = "en_us";

    /*
     * Map of translations for the tip
     * Key: language code
     * Value: translated tip
     */
    private final Map<String, String> translations = new HashMap<>();

    public TranslatableTip(String defaultTranslation) {
        translations.put("en_us", defaultTranslation);
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
}

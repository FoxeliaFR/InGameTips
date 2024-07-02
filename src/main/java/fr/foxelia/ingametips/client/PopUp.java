package fr.foxelia.ingametips.client;

public class PopUp implements ITip {

    private final int displayTime;
    private final String message;

    public PopUp(String message, int displayTime) {
        this.message = message;
        this.displayTime = displayTime;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getBlitOffset(int lines) {
        switch (lines) {
            case 1, 2: return 0;
            case 3, 4: return 32;
            case 5, 6: return 80;
            default: return 144;
        }
    }

    public int getBackgroundHeight(int lines) {
        switch (lines) {
            case 1, 2: return 32;
            case 3, 4: return 48;
            case 5, 6: return 64;
            default: return 96;
        }
    }

    public int getDisplayTime() {
        return displayTime;
    }
}

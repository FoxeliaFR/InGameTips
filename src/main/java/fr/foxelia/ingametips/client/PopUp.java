package fr.foxelia.ingametips.client;

public class PopUp implements ITip {

    private boolean shown = false;
    private int displayTime = 5000;

    @Override
    public String getTip() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    }

    public boolean isShown() {
        return shown;
    }

    protected void setShown(boolean shown) {
        this.shown = shown;
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

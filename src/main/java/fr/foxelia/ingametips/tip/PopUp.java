package fr.foxelia.ingametips.tip;

public record PopUp(String message, int displayTime) implements ITip {

    public int getBlitOffset(int lines) {
        return switch (lines) {
            case 1, 2 -> 0;
            case 3, 4 -> 32;
            case 5, 6 -> 80;
            default -> 144;
        };
    }

    public int getBackgroundHeight(int lines) {
        return switch (lines) {
            case 1, 2 -> 32;
            case 3, 4 -> 48;
            case 5, 6 -> 64;
            default -> 96;
        };
    }
}

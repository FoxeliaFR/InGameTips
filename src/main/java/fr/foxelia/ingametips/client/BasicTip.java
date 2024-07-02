package fr.foxelia.ingametips.client;

public record BasicTip(String message, int displayTime) implements ITip {

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getDisplayTime() {
        return displayTime;
    }
}

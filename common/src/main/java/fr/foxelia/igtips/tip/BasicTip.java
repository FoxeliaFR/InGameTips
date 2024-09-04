package fr.foxelia.igtips.tip;

public record BasicTip(String message, int displayTime) implements ITip {

    @Override
    public String message() {
        return message;
    }

    @Override
    public int displayTime() {
        return displayTime;
    }
}

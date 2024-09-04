package fr.foxelia.igtips.tip;

public record PopUp(String message, int displayTime) implements ITip {

    public int getBackgroundHeight(int numLines) {
        return TexturePart.TOP.height() + TexturePart.MIDDLE.height() * (numLines - 1) + TexturePart.BOTTOM.height();
    }

    public enum TexturePart {
        TOP(0, 16),
        MIDDLE(16, 16),
        BOTTOM(32, 16);

        private final int offset;
        private final int height;

        TexturePart(int offset, int height) {
            this.offset = offset;
            this.height = height;
        }

        public int offset() {
            return offset;
        }

        public int height() {
            return height;
        }
    }

}

package fr.foxelia.igtips.client.overlay;

import fr.foxelia.igtips.client.config.ClientConfig;
import fr.foxelia.igtips.tip.PopUp;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;

import java.util.ArrayList;
import java.util.List;

public class PopUpAnimation {

    private static final int ANIMATION_DURATION = 300;
    private static final int MAX_LINE_WIDTH = 240;
    private static final int OFFSET = 4;
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final PopUp current;
    private final long startEnd;
    private final List<OrderedText> lines;
    private final int backgroundHeight;
    public long animationStartTime = System.currentTimeMillis(); // A changer (private)
    private long elapsed = 0;
    private boolean needSound = true;
    private boolean endSoundPlayed = false;

    PopUpAnimation(PopUp popUp) {
        this.current = popUp;
        startEnd = current.displayTime() - ANIMATION_DURATION;
        TextRenderer fontRenderer = mc.textRenderer;
        lines = new ArrayList<>(fontRenderer.wrapLines(StringVisitable.plain(popUp.message()), MAX_LINE_WIDTH));
        int maxLines = ClientConfig.getMaxLines();
        if(lines.size() > maxLines) {
            lines.subList(maxLines, lines.size()).clear();
        }
        backgroundHeight = popUp.getBackgroundHeight(lines.size());
    }

    private float getAnimationProgress() {
        elapsed = System.currentTimeMillis() - this.animationStartTime;

        if(elapsed > startEnd) {
            if(!endSoundPlayed) needSound = true;
            return Math.min((float) (current.displayTime() - elapsed) / ANIMATION_DURATION, 1.0f);
        } else if (elapsed > ANIMATION_DURATION) {
            return 1.0f;
        } else {
            return Math.min((float) elapsed / ANIMATION_DURATION, 1.0f);
        }
    }

    public int getY() {
        float progress = getAnimationProgress();
        if(progress == 1.0f) return OFFSET;
        int startY = -backgroundHeight;
        return startY + (int) ((OFFSET - startY) * progress);
    }

    public PopUp getCurrent() {
        return current;
    }

    public void verifySoundEvent() {
        if(needSound) {
            if(elapsed > startEnd) {
                playUISound(SoundEvents.UI_TOAST_OUT);
                endSoundPlayed = true;
            } else playUISound(SoundEvents.UI_TOAST_IN);
            needSound = false;
        }
    }

    public void playUISound(SoundEvent sound) {
        if(ClientConfig.isSoundEnabled()) mc.getSoundManager().play(PositionedSoundInstance.master(sound, 1.0F, 1.0F));
    }

    public boolean isAnimationFinished() {
        return System.currentTimeMillis() - this.animationStartTime > current.displayTime();
    }

    public List<OrderedText> getLines() {
        return lines;
    }



}

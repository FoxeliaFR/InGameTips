package fr.foxelia.ingametips.overlay;

import fr.foxelia.ingametips.tip.PopUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class PopUpAnimation {

    private static final int ANIMATION_DURATION = 300;
    private static final int MAX_LINE_WIDTH = 240;
    private static final int OFFSET = 4;
    private static final int MAX_LINES = 7;
    private final Minecraft mc = Minecraft.getInstance();
    private final PopUp current;
    private final long startEnd;
    private final List<FormattedCharSequence> lines;
    private final int backgroundHeight;
    public long animationStartTime = System.currentTimeMillis(); // A changer (private)
    private long elapsed = 0;
    private boolean playSound = true;
    private boolean endSoundPlayed = false;

    PopUpAnimation(PopUp popUp) {
        this.current = popUp;
        startEnd = current.displayTime() - ANIMATION_DURATION;
        Font fontRenderer = mc.font;
        lines = new ArrayList<>(fontRenderer.split(FormattedText.of(popUp.message()), MAX_LINE_WIDTH));
        if(lines.size() > MAX_LINES) {
            lines.subList(MAX_LINES, lines.size()).clear();
        }
        backgroundHeight = popUp.getBackgroundHeight(lines.size());
    }

    private float getAnimationProgress() {
        elapsed = System.currentTimeMillis() - this.animationStartTime;

        if(elapsed > startEnd) {
            if(!endSoundPlayed) playSound = true;
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

    public void playSound() {
        if(playSound) {
            if(elapsed > startEnd) {
                mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_OUT, 1.0F, 1.0F));
                endSoundPlayed = true;
            } else {
                mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_IN, 1.0F, 1.0F));
            }
            playSound = false;
        }
    }

    public boolean isAnimationFinished() {
        return System.currentTimeMillis() - this.animationStartTime > current.displayTime();
    }

    public List<FormattedCharSequence> getLines() {
        return lines;
    }



}

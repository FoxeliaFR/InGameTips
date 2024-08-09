package fr.foxelia.ingametips.overlay;

import fr.foxelia.ingametips.config.InGameTipsClientConfigs;
import fr.foxelia.ingametips.tip.PopUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class PopUpAnimation {

    private static final int ANIMATION_DURATION = 300;
    private static final int MAX_LINE_WIDTH = 240;
    private static final int OFFSET = 4;
    private final Minecraft mc = Minecraft.getInstance();
    private final PopUp current;
    private final long startEnd;
    private final List<FormattedCharSequence> lines;
    private final int backgroundHeight;
    public long animationStartTime = System.currentTimeMillis(); // A changer (private)
    private long elapsed = 0;
    private boolean needSound = true;
    private boolean endSoundPlayed = false;

    PopUpAnimation(PopUp popUp) {
        this.current = popUp;
        startEnd = current.displayTime() - ANIMATION_DURATION;
        Font fontRenderer = mc.font;
        lines = new ArrayList<>(fontRenderer.split(FormattedText.of(popUp.message()), MAX_LINE_WIDTH));
        if(lines.size() > InGameTipsClientConfigs.maxLines.get()) {
            lines.subList(InGameTipsClientConfigs.maxLines.get(), lines.size()).clear();
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
        if(InGameTipsClientConfigs.enableSound.get()) mc.getSoundManager().play(SimpleSoundInstance.forUI(sound, 1.0F, 1.0F));
    }

    public boolean isAnimationFinished() {
        return System.currentTimeMillis() - this.animationStartTime > current.displayTime();
    }

    public List<FormattedCharSequence> getLines() {
        return lines;
    }



}

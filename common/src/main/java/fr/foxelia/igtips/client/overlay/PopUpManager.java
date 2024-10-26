package fr.foxelia.igtips.client.overlay;

import com.google.common.collect.Queues;
import fr.foxelia.igtips.client.config.ClientConfig;
import fr.foxelia.igtips.tip.PopUp;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;

public class PopUpManager {

    private static PopUpManager instance;
    private final Deque<PopUp> queued = Queues.newArrayDeque();
    private PopUpAnimation current = null;

    PopUpManager() {
        instance = this;
    }

    public static PopUpManager getInstance() {
        return instance;
    }

    public void queue(PopUp popUp) {
        if(ClientConfig.isTipsEnabled()) queued.add(popUp);
    }

    @Nullable
    public PopUp getQueued() {
        if(queued.isEmpty()) return null;
        PopUp popUp = queued.peek();
        if(current == null || current.getCurrent() != popUp) {
            current = new PopUpAnimation(popUp);
        } else {
            if(current.isAnimationFinished()) {
                queued.poll();
                current = null;
                return null;
            }
        }
        return popUp;
    }

    public PopUpAnimation getAnimation() {
        return current;
    }

    public static void register() {
        new PopUpManager();
    }

}

package fr.foxelia.ingametips;

import com.google.common.collect.Queues;
import fr.foxelia.ingametips.client.PopUp;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;

public class PopUpManager {

    private static PopUpManager instance;
    private final Deque<PopUp> queued = Queues.newArrayDeque();
    private TipAnimation current = null;

    PopUpManager() {
        instance = this;
    }

    public static PopUpManager getInstance() {
        return instance;
    }

    public void queue(PopUp popUp) {
        queued.add(popUp);
    }

    @Nullable
    public PopUp getQueued() {
        if(queued.isEmpty()) return null;
        PopUp popUp = queued.peek();
        if(current == null || current.getCurrent() != popUp) {
            current = new TipAnimation(popUp);
        } else {
            if(current.isAnimationFinished()) {
                queued.poll();
                current = null;
                return null;
            }
        }
        return popUp;
    }

    public TipAnimation getAnimation() {
        return current;
    }

}

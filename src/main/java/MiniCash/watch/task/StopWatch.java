package MiniCash.watch.task;

import MiniCash.watch.TimerManager;
import MiniCash.watch.model.DisplayType;
import MiniCash.watch.show.DisplayRenderer;

public class StopWatch extends AbstractTask {

    private final TimerManager timerManager;

    public StopWatch(DisplayType displayType, DisplayRenderer displayRenderer , TimerManager timerManager) {
        super(0, displayType, displayRenderer);
        this.timerManager = timerManager;
    }

    @Override
    protected void tick() {

        if (timerManager.isShowStopWatch()) {
            displayRenderer.renderToAll(formatTime(), displayType , 1.0);
        } else {

            displayRenderer.clear();

        }

        seconds++;
    }

}

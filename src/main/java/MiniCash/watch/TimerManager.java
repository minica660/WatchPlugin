package MiniCash.watch;

import MiniCash.watch.model.DisplayType;
import MiniCash.watch.show.DisplayRenderer;
import MiniCash.watch.task.StopWatch;
import MiniCash.watch.task.Timer;

public class TimerManager {

    private Watch plugin;
    private Timer timerTask;
    private StopWatch stopwatchTask;
    private boolean showStopWatch = true;

    public TimerManager(Watch plugin) {
        this.plugin = plugin;
    }

    public boolean startTimer(int seconds, DisplayType displayType){

        if(timerTask != null){
            return false;
        }

        DisplayRenderer displayRenderer = new DisplayRenderer();

        timerTask = new Timer(seconds, displayType, displayRenderer , this);
        timerTask.runTaskTimer(plugin, 0L, 20L);
        return true;


    }

    public boolean startStopWatch(DisplayType displayType){
        if (stopwatchTask != null) {
            return false;
        }
        DisplayRenderer renderer = new DisplayRenderer();
        stopwatchTask = new StopWatch(displayType, renderer, this);
        stopwatchTask.runTaskTimer(plugin, 0L, 20L);
        return true;
    }

    public void stopTimer(){

        if(timerTask != null){
            timerTask.getRenderer().clear();
            timerTask.cancel();
            timerTask = null;
        }

    }

    public void stopStopWatch(){

        if (stopwatchTask != null) {
            stopwatchTask.getRenderer().clear();
            stopwatchTask.cancel();
            stopwatchTask = null;
        }

    }

    public void toggleShowStopWatch(){
        // 現在の値の逆にする
        this.showStopWatch = !this.showStopWatch;
    }

    public boolean isShowStopWatch(){
        return showStopWatch;
    }

    public void stopAll() {
        stopTimer();
        stopStopWatch();
    }

    public Timer getTimerTask() {
        return timerTask;
    }

    public StopWatch getStopwatchTask() {
        return stopwatchTask;
    }


}

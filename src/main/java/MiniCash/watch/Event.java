package MiniCash.watch;

import MiniCash.watch.show.DisplayRenderer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {

    private final TimerManager timerManager;

    public Event(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        /*
            タイマー、ストップウォッチがそれぞれ起動中だったら処理
         */
        if (timerManager.getTimerTask() != null) {
            DisplayRenderer displayRenderer = timerManager.getTimerTask().getRenderer();
            if (displayRenderer.getBossBar() != null) {
                displayRenderer.getBossBar().addPlayer(event.getPlayer());
            }
        }


        if (timerManager.getStopwatchTask() != null && timerManager.isShowStopWatch()) {
            DisplayRenderer displayRenderer = timerManager.getStopwatchTask().getRenderer();
            if (displayRenderer.getBossBar() != null) {
                displayRenderer.getBossBar().addPlayer(event.getPlayer());
            }
        }

    }

}

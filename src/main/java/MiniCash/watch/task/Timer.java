package MiniCash.watch.task;

import MiniCash.watch.TimerManager;
import MiniCash.watch.model.DisplayType;
import MiniCash.watch.show.DisplayRenderer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;

public class Timer extends AbstractTask {

    private final int maxSeconds;
    private final TimerManager timerManager;
    public Timer(int seconds, DisplayType displayType, DisplayRenderer displayRenderer , TimerManager timerManager) {
        super(seconds, displayType, displayRenderer);
        this.maxSeconds = seconds;
        this.timerManager = timerManager;
    }

    Sound sound = Sound.sound(Key.key("block.anvil.use"), Sound.Source.MASTER, 10f, 0.9f);

    @Override
    protected void tick() {
        if (seconds <= 0) {
            // 時間終了処理
            displayRenderer.renderToAll("00:00 - 終了！", displayType ,0.0);

            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(sound);
            });

            timerManager.stopTimer();
            this.cancel();
            return;
        }

        double progress = (double) seconds / maxSeconds;
        displayRenderer.renderToAll(formatTime(), displayType,progress);

        seconds--;

    }
}

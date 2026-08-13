package MiniCash.watch.task;

import MiniCash.watch.model.DisplayType;
import MiniCash.watch.show.DisplayRenderer;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractTask extends BukkitRunnable {

    protected int seconds;
    protected DisplayType displayType;
    protected DisplayRenderer displayRenderer;

    public AbstractTask(int seconds, DisplayType displayType, DisplayRenderer displayRenderer) {
        this.seconds = seconds;
        this.displayType = displayType;
        this.displayRenderer = displayRenderer;
    }

    @Override
    public void run() {
        tick();
    }

    /**
     * タイマーとストップウォッチそれぞれで固有の処理（時間の加減算や表示制御）を実装する抽象メソッド
     */
    protected abstract void tick();

    protected String formatTime() {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    public DisplayRenderer getRenderer() {
        return displayRenderer;
    }
}

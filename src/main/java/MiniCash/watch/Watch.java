package MiniCash.watch;

import MiniCash.watch.command.WatchCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Watch extends JavaPlugin {

    private TimerManager timerManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.timerManager = new TimerManager(this);

        getServer().getPluginManager().registerEvents(new Event(timerManager), this);

        registerCommand("watch","タイマー、ストップウォッチを管理するプラグインのコマンド" , new WatchCommand(this,timerManager));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (timerManager != null) {
            timerManager.stopAll();
        }
    }
}

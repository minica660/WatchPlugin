package MiniCash.watch.show;

import MiniCash.watch.model.DisplayType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class DisplayRenderer {

    private static BossBar bossBar;

    public void renderToAll(String timeString , DisplayType displayType , double progress) {

        switch (displayType) {
            case TITLE -> {

                clearBossBar();

                Title title = Title.title(
                        Component.text(timeString)
                        ,Component.text("")
                );

                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.showTitle(title);
                });

                break;

            }
            case ACION_BAR -> {

                clearBossBar();

                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendActionBar(Component.text(timeString));
                });

                break;

            }
            case BOSS_BAR -> {

                if (bossBar == null) {

                    bossBar = Bukkit.createBossBar(
                            timeString, BarColor.GREEN, BarStyle.SEGMENTED_12
                    );

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        bossBar.addPlayer(player);
                    });

                }

                bossBar.setTitle(timeString);
                bossBar.setProgress(Math.clamp(progress, 0.0, 1.0));
                bossBar.setVisible(true);
                break;

            }
        }


    }

    public void clear() {
        clearBossBar();
    }

    private void clearBossBar() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }
    }

    public BossBar getBossBar() {
        return bossBar;
    }

}

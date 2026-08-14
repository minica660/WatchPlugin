package MiniCash.watch.command;

import MiniCash.watch.TimerManager;
import MiniCash.watch.Watch;
import MiniCash.watch.model.DisplayType;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WatchCommand implements BasicCommand {

    List<Component> helpMessage = new ArrayList<>();

    final Watch plugin;
    final TimerManager timerManager;

    public WatchCommand(Watch plugin,TimerManager timerManager) {
        this.plugin = plugin;
        this.timerManager = timerManager;

        helpMessage.add(Component.text("タイマーを起動 /watch timer <seconds> <displayType>", NamedTextColor.WHITE));
        helpMessage.add(Component.text("ストップウォッチを起動 /watch stopwatch <displayType>", NamedTextColor.WHITE));
        helpMessage.add(Component.text("ストップウォッチの表示切替 /watch toggle", NamedTextColor.WHITE));
        helpMessage.add(Component.text("現在有効なタイマーを停止 /watch stop timer", NamedTextColor.WHITE));
        helpMessage.add(Component.text("現在有効なストップウォッチを停止 /watch stop stopwatch", NamedTextColor.WHITE));



    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {

        CommandSender sender = commandSourceStack.getSender();

        if(args.length == 0) {

            for(Component component : helpMessage) {

                sender.sendMessage(
                        getMessage(
                                component
                        )
                );

            }

            return;
        }


        switch (args[0]) {
            case "timer":

                if(args.length == 3) {

                    int seconds = 0;
                    DisplayType displayType;

                    try {
                       seconds = Integer.parseInt(args[1]);
                    }catch(NumberFormatException e) {
                        sender.sendMessage(
                                getMessage(
                                        Component.text("タイマー時刻には数値を入力してください", NamedTextColor.RED)
                                )
                        );
                        return;
                    }

                    try {
                        displayType = DisplayType.valueOf(args[2]);
                    }catch(IllegalArgumentException e) {
                        sender.sendMessage(
                                getMessage(
                                        Component.text(e.getMessage(), NamedTextColor.RED)
                                )
                        );
                        return;
                    }

                    boolean timerStarted = timerManager.startTimer(seconds, displayType);

                    if (!timerStarted) {
                        sender.sendMessage(
                                getMessage(
                                        Component.text("すでにタイマーが起動しています", NamedTextColor.RED)
                                )
                        );
                        sender.sendMessage(
                                getMessage(
                                        Component.text("現在有効なタイマーを止めたうえで再度このコマンドを実行してください",NamedTextColor.RED)
                                )
                        );

                        return;

                    } else {
                        sender.sendMessage(
                                getMessage(
                                        Component.text("タイマーを時間：" + seconds + "秒 , 表示タイプ:" + displayType.name() + "で開始しました",NamedTextColor.GREEN)
                                )
                        );
                    }
                    break;

                }else {
                    sender.sendMessage(
                            getMessage(
                                    helpMessage.get(0)
                            )
                    );
                }

            case "stopwatch":

                if(args.length == 2) {

                    DisplayType displayType;

                    try {
                        displayType = DisplayType.valueOf(args[1]);
                    }catch(IllegalArgumentException e) {
                        sender.sendMessage(
                                getMessage(
                                        Component.text(e.getMessage(), NamedTextColor.RED)
                                )
                        );
                        return;
                    }

                    boolean stopWatchStarted = timerManager.startStopWatch(displayType);

                    if (!stopWatchStarted) {
                        sender.sendMessage(
                                getMessage(
                                        Component.text("すでにストップウォッチが起動しています", NamedTextColor.RED)
                                )
                        );
                        sender.sendMessage(
                                getMessage(
                                        Component.text("現在有効なストップウォッチを止めたうえで再度このコマンドを実行してください",NamedTextColor.RED)
                                )
                        );

                        return;

                    } else {
                        sender.sendMessage(
                                getMessage(
                                        Component.text("ストップウォッチを表示タイプ: " + displayType.name() + "で開始しました",NamedTextColor.GREEN)
                                )
                        );
                    }
                    break;

                }else {
                    sender.sendMessage(
                            getMessage(
                                    helpMessage.get(1)
                            )
                    );
                }

            case "toggle":

                timerManager.toggleShowStopWatch();

                String onOff = timerManager.isShowStopWatch() ? "有効" : "無効";

                sender.sendMessage(
                        getMessage(
                            Component.text("ストップウォッチの表示を" + onOff + "にしました",NamedTextColor.YELLOW)
                        )
                );
                break;

            case "stop":

                if(args.length == 2) {

                    if(args[1].equals("timer")){
                        timerManager.stopTimer();
                        sender.sendMessage(
                                getMessage(
                                        Component.text("タイマーを停止しました")
                                )
                        );

                    }else if(args[1].equals("stopwatch")){
                        timerManager.stopStopWatch();
                        sender.sendMessage(
                                getMessage(
                                        Component.text("ストップウォッチを停止しました")
                                )
                        );
                    }else {

                        sender.sendMessage(
                                helpMessage.get(4)
                        );
                        sender.sendMessage(
                                helpMessage.get(5)
                        );

                    }


                }else {
                    sender.sendMessage(
                            getMessage(
                                    helpMessage.get(4)
                            )
                    );
                    sender.sendMessage(
                            helpMessage.get(5)
                    );
                }
                break;

        }

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 0 || args.length == 1) {
            List<String> subCommands = List.of("timer", "stopwatch", "toggle", "stop");
            String current = (args.length == 0) ? "" : args[0];
            return filterSuggest(subCommands, current);
        }

        if (args.length > 1) {
            String subCommand = args[0].toLowerCase();

            switch (subCommand) {
                case "timer":
                    if (args.length == 2) {

                        return filterSuggest(List.of("5", "30", "60", "180", "300"), args[1]);

                    } else if (args.length == 3) {
                        return filterSuggest(getDisplayTypeNameList(), args[2]);
                    }
                    break;

                case "stopwatch":
                    if (args.length == 2) {
                        return filterSuggest(getDisplayTypeNameList(), args[1]);
                    }
                    break;

                case "stop":
                    if (args.length == 2) {
                        return filterSuggest(List.of("timer", "stopwatch"), args[1]);
                    }
                    break;
            }
        }

        return completions;
    }

    private List<String> filterSuggest(List<String> candidates, String current) {
        List<String> result = new ArrayList<>();
        String lowerCurrent = current.toLowerCase();

        for (String candidate : candidates) {
            if (candidate.toLowerCase().startsWith(lowerCurrent)) {
                result.add(candidate);
            }
        }
        return result;
    }

    private List<String> getDisplayTypeNameList() {
        List<String> displayTypeNames = new ArrayList<>();
        for (DisplayType type : DisplayType.values()) {
            displayTypeNames.add(type.name());
        }
        return displayTypeNames;
    }

    @Override
    public @Nullable String permission() {
        return BasicCommand.super.permission();
    }


    public static Component getMessage(Component message){
        return Component.text("[").color(NamedTextColor.DARK_GRAY).append(Component.text("Watch").color(NamedTextColor.GOLD).append(Component.text("]").color(NamedTextColor.DARK_GRAY)
                .append(message)
        ));
    }
}

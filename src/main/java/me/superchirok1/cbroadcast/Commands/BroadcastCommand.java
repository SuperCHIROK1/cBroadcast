package me.superchirok1.cbroadcast.Commands;

import me.superchirok1.cbroadcast.CBroadcast;
import me.superchirok1.cbroadcast.Utils.ColorUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class BroadcastCommand implements CommandExecutor {

    private final CBroadcast plugin;

    public BroadcastCommand(CBroadcast plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("cbr.broadcast")) {
            if (args.length == 0) {
                String raw = plugin.getLocale().get("usage");
                sender.sendMessage(ColorUtils.translateHex(raw));
                return true;
            }

            String messag = args[0];
            String message = messag.replace(Objects.requireNonNull(plugin.getConfig().getString("space-symbol")), " ");

            int fadeIn = plugin.getConfig().getInt("title.fade-in") * 20;
            int stay = plugin.getConfig().getInt("title.stay") * 20;
            int fadeOut = plugin.getConfig().getInt("title.fade-out") * 20;

            if (plugin.getConfig().getBoolean("title.enabled")) {
                Bukkit.getServer().getOnlinePlayers().forEach(player ->
                {
                    String subtitl = ColorUtils.translateHex(plugin.getConfig().getString("title.subtitle"));
                    String subtitle = subtitl.replace("{sender_name}", sender.getName());

                    String titl = ColorUtils.translateHex(plugin.getConfig().getString("title.title"));
                    String title = titl.replace("{message}", message);
                    player.sendTitle(ColorUtils.translateHex(title), subtitle, fadeIn, stay, fadeOut);
                });
            }

            if (plugin.getConfig().getBoolean("sound.enabled")) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    String soundid = plugin.getConfig().getString("sound.id");
                    int volume = plugin.getConfig().getInt("sound.volume");
                    int pitch = plugin.getConfig().getInt("sound.pitch");

                    player.playSound(player.getLocation(), Sound.valueOf(soundid), volume, pitch);
                }
            }

            if (plugin.getConfig().getBoolean("chat.enabled")) {
                List<String> rawMessages = plugin.getConfig().getStringList("chat.messages");

                for (String raw : rawMessages) {
                    String replaced = raw
                            .replace("{sender_name}", sender.getName())
                            .replace("{message}", message);

                    String chatMessages = ColorUtils.translateHex(replaced);
                    Bukkit.broadcastMessage(chatMessages);
                }
            }


            if (plugin.getConfig().getBoolean("actionbar.enabled")) {
                String actionbartxt = plugin.getConfig().getString("actionbar.text");
                String replacedactionbar = actionbartxt
                        .replace("{sender_name}", sender.getName())
                        .replace("{message}", message);
                String actionbarmessage = ColorUtils.translateHex(replacedactionbar);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    Player target = player;

                    new BukkitRunnable() {
                        int ticks = plugin.getConfig().getInt("actionbar.stay") * 20;

                        @Override
                        public void run() {
                            if (ticks <= 0 || !target.isOnline()) {
                                this.cancel();
                                return;
                            }

                            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionbarmessage));
                            ticks -= 20;
                        }
                    }.runTaskTimer(plugin, 0L, 20L);
                }
            }


            if (plugin.getConfig().getBoolean("bossbar.enabled")) {
                Bukkit.getServer().getOnlinePlayers().forEach(player ->
                {
                    String bossbarmessag = ColorUtils.translateHex(plugin.getConfig().getString("bossbar.text"));
                    String bossbarmessagi = bossbarmessag.replace("{sender_name}", sender.getName());
                    String bossbarmessage = bossbarmessagi.replace("{message}", message);

                    String bossbarColor = ColorUtils.translateHex(plugin.getConfig().getString("bossbar.color"));
                    String bossbarStyle = ColorUtils.translateHex(plugin.getConfig().getString("bossbar.style"));

                    BossBar bossBar = Bukkit.createBossBar(bossbarmessage, BarColor.valueOf(bossbarColor), BarStyle.valueOf(bossbarStyle));
                    bossBar.setVisible(true);

                    bossBar.addPlayer(player);

                    int bossbarStay = plugin.getConfig().getInt("bossbar.stay-seconds");

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        bossBar.removeAll();
                    }, bossbarStay * 20L);
                });
            }

            int online = Bukkit.getOnlinePlayers().size();

            String raw = plugin.getLocale().get("sending-successful");
            String sendplaceholder1 = raw.replace("{sender_name}", sender.getName());
            String sendplaceholder2 = sendplaceholder1.replace("{players_count}", String.valueOf(online));
            String sending = sendplaceholder2.replace("{message}", message);

            sender.sendMessage(ColorUtils.translateHex(sending));

            return true;
        } else {
            sender.sendMessage(ColorUtils.translateHex(plugin.getLocale().get("no-permission")));
        }

        return true;
    }
}

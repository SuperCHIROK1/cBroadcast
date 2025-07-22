package me.superchirok1.cbroadcast.Utils;

import me.superchirok1.cbroadcast.CBroadcast;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static CBroadcast plugin;

    public static void init(CBroadcast pluginInstance) {
        plugin = pluginInstance;
    }

    public static String translateHex(String message) {
        if (message == null) return "";

        String prefix = plugin.getLocale().get("prefix");
        message = message.replace("{PRFX}", prefix);

        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.of("#" + hex).toString());
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}

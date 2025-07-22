package me.superchirok1.cbroadcast;

import me.superchirok1.cbroadcast.Commands.BroadcastCommand;
import me.superchirok1.cbroadcast.Commands.System;
import me.superchirok1.cbroadcast.Managers.LocaleManager;
import me.superchirok1.cbroadcast.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public final class CBroadcast extends JavaPlugin {

    private LocaleManager localeManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        getCommand("cbr").setExecutor(new System(this));
        getCommand("br").setExecutor(new BroadcastCommand(this));
        localeManager = new LocaleManager(this);
        ColorUtils.init(this);
        saveLocaleFiles();
        printAsciiBanner();

    }

    public void printAsciiBanner() {
        sendHexToConsole("&8&m                            &f");
        sendHexToConsole(" &#ff3333  _____ ____  ");
        sendHexToConsole(" &#ff3333 / ____|  _ \\ ");
        sendHexToConsole(" &#ff3333| |    | |_) |");
        sendHexToConsole(" &#ff3333| |    |  _ < ");
        sendHexToConsole(" &#ff3333| |____| |_) |");
        sendHexToConsole(" &#ff3333 \\_____|____/ ");
        sendHexToConsole(" ");
        sendHexToConsole(" &7cBroadcast &8| &aEnabled &7✔");
        sendHexToConsole(" &7Version: &#ff3333" + getDescription().getVersion() + " &8| &7Locale: &#ff3333" + getConfig().getString("locale").toUpperCase());
        sendHexToConsole(" &7Author: &#ff3333SuperCHIROK1");
        sendHexToConsole("&8&m                            &f");
    }

    public void printAsciiBannerDis() {
        sendHexToConsole("&8&m                            &f");
        sendHexToConsole(" &#ff3333  _____ ____  ");
        sendHexToConsole(" &#ff3333 / ____|  _ \\ ");
        sendHexToConsole(" &#ff3333| |    | |_) |");
        sendHexToConsole(" &#ff3333| |    |  _ < ");
        sendHexToConsole(" &#ff3333| |____| |_) |");
        sendHexToConsole(" &#ff3333 \\_____|____/ ");
        sendHexToConsole(" ");
        sendHexToConsole(" &7cBroadcast &8| &cDisabled");
        sendHexToConsole(" &7Version: &#ff3333" + getDescription().getVersion() + " &8| &7Locale: &#ff3333" + getConfig().getString("locale").toUpperCase());
        sendHexToConsole(" &7Author: &#ff3333SuperCHIROK1");
        sendHexToConsole("&8&m                            &f");
    }

    private void sendHexToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtils.translateHex(message));
    }


    private void saveLocaleFiles() {
        List<String> locales = Arrays.asList("en", "ru", "de");

        for (String locale : locales) {
            String path = "messages/messages-" + locale + ".yml";
            File file = new File(getDataFolder(), path);
            if (!file.exists()) {
                saveResource(path, false);
            }
        }
    }


    public LocaleManager getLocale() {
        return localeManager;
    }

    @Override
    public void onDisable() {
        printAsciiBannerDis();
    }
}

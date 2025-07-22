package me.superchirok1.cbroadcast.Managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocaleManager {

    private final JavaPlugin plugin;
    private YamlConfiguration messages;

    private final List<String> supportedLocales = Arrays.asList("en", "ru", "de");

    public LocaleManager(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        String locale = plugin.getConfig().getString("locale", "en").toLowerCase();

        if (!supportedLocales.contains(locale)) {
            plugin.getLogger().warning("Unknown locale: '" + locale + "', defaulting to 'en'");
            locale = "en";
        }

        String path = "messages/messages-" + locale + ".yml";

        File file = new File(plugin.getDataFolder(), path);
        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

        messages = YamlConfiguration.loadConfiguration(file);

        InputStream stream = plugin.getResource(path);
        if (stream != null) {
            Reader reader = new InputStreamReader(stream);
            YamlConfiguration def = YamlConfiguration.loadConfiguration(reader);
            messages.setDefaults(def);
        }
    }

    public String get(String key) {
        return Objects.requireNonNullElse(messages.getString(key), "§cMissing locale key: " + key);
    }
}

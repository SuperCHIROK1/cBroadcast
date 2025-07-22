package me.superchirok1.cbroadcast.Commands;

import me.superchirok1.cbroadcast.CBroadcast;
import me.superchirok1.cbroadcast.Utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class System implements CommandExecutor {

    private final String prfx;

    private final CBroadcast plugin;

    public System(CBroadcast plugin) {
        this.plugin = plugin;
        this.prfx = "&#FD1C1C&lc&#FD2727&lB&#FD3131&lr&#FE3C3C&lo&#FE4747&la&#FE5151&ld&#FE5C5C&lc&#FF6767&la&#FF7171&ls&#FF7C7C&lt";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("about")) {
            sender.sendMessage(ColorUtils.translateHex(" "));
            sender.sendMessage(ColorUtils.translateHex(" " + prfx + " &7(&#FF7C7C" + plugin.getDescription().getVersion() + "&7)"));
            sender.sendMessage(ColorUtils.translateHex(" &7Broadcast Plugin"));
            sender.sendMessage(ColorUtils.translateHex(" "));
            sender.sendMessage(ColorUtils.translateHex(" &fBy: &#FF7C7CSuperCHIROK1"));
            sender.sendMessage(ColorUtils.translateHex(" &fGitHub: &#FF7C7Chttps://github.com/SuperCHIROK1/cBroadcast"));
            sender.sendMessage(ColorUtils.translateHex(" "));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("cbr.reload")) {
                String msg = plugin.getLocale().get("no-permission");
                sender.sendMessage(ColorUtils.translateHex(msg));
            } else {
                plugin.reloadConfig();
                String msg = plugin.getLocale().get("reloaded");
                sender.sendMessage(ColorUtils.translateHex(msg));
                plugin.getLocale().load();
            }
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ColorUtils.translateHex(" "));
        sender.sendMessage(ColorUtils.translateHex(" " + prfx + " &7(&#FF7C7C" + plugin.getDescription().getVersion() + "&7)"));
        sender.sendMessage(ColorUtils.translateHex(" &8&m    &f"));
        sender.sendMessage(ColorUtils.translateHex(" &#FF7C7C/cbr about &7- &fAbout plugin"));
        sender.sendMessage(ColorUtils.translateHex(" &#FF7C7C/cbr reload &7- &fReloads plugin"));
        sender.sendMessage(ColorUtils.translateHex(" &#FF7C7C/br <message> &7- &fSent broadcast message"));
        sender.sendMessage(ColorUtils.translateHex(" "));
    }
}

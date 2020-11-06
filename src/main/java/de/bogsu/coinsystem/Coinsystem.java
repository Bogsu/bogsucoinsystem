package de.bogsu.coinsystem;

import de.bogsu.coinsystem.commands.CoinCreateCommand;
import de.bogsu.coinsystem.commands.CoinDeleteCommand;
import de.bogsu.coinsystem.commands.CoinManagementCommand;
import de.bogsu.coinsystem.listener.CoinManagementListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import sun.jvm.hotspot.ui.ObjectHistogramPanel;

/**
 * Coinsystem - Klasse um das Plugin zu laden und Listener, Commands vorzubereiten
 */
public final class Coinsystem extends JavaPlugin {

    public static String PREFIX = "§aCoinsystem §7§o";
    public static Coinsystem INSTANCE;

    public Coinsystem(){
        INSTANCE = this;
    }

    /**
     * Plugin Ablauf beim Start des Plugins
     */
    @Override
    public void onEnable() {
        this.register();
        log("Plugin geladen.");
    }

    /**
     * Plugin Ablauf beim Stoppen des Plugins
     */
    @Override
    public void onDisable() {
        log("Plugin gestoppt.");
    }

    /**
     * Plugin Konsolenausgabe beim Starten/Stoppen des Plugins
     */
    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

    /**
     * Methode um Listener und Commands beim starten des Plugins für die Verwendung vorzubereiten
     */
    private void register(){
        //Listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CoinManagementListener(), this);

        //Commands
        Bukkit.getPluginCommand("coinmanagement").setExecutor(new CoinManagementCommand());
        Bukkit.getPluginCommand("coincreate").setExecutor(new CoinCreateCommand());
        Bukkit.getPluginCommand("coindelete").setExecutor(new CoinDeleteCommand());
    }
}

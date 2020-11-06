package de.bogsu.coinsystem.commands;

import de.bogsu.coinsystem.Coinsystem;
import de.bogsu.coinsystem.database.DataBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CoinManagementCommand - Diese Klasse bereitet einen Befehl vor, mit welchem der Benutzer seine Konten auflisten kann
 */
public class CoinManagementCommand implements CommandExecutor {

    /**
     * Methode, welche ausgeführt wird sobald der Benutzer den coinmanagement-Command eingegeben hat
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            Coinsystem.INSTANCE.log("Du bist kein Spieler.");
            return true;
        }

        Player player = (Player) sender;

        DataBase db = new DataBase();
        player.sendMessage("Deine Konten: "+db.selectAccounts(player.getName()));

        return true;
    }
}

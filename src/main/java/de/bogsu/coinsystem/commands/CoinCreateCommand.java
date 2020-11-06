package de.bogsu.coinsystem.commands;

import de.bogsu.coinsystem.Coinsystem;
import de.bogsu.coinsystem.database.DataBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CoinCreateCommand - Diese Klasse bereitet einen Befehl vor, mit welchem der Benutzer ein neues Konto erstellen kann
 */
public class CoinCreateCommand implements CommandExecutor {

    /**
     * Methode, welche ausgeführt wird sobald der Benutzer den coincreate-Command eingegeben hat
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            Coinsystem.INSTANCE.log("Du bist kein Spieler.");
            return true;
        }

        Player player  = (Player) sender;

        try{
            DataBase db = new DataBase();
            db.insertAccount(args[0], player.getName(), 0);
            player.sendMessage("Konto wurde erstellt");
        } catch  (Exception e) {
            player.sendMessage("Das Konto konnte nicht erstellt werden");
        }

        return true;
    }
}

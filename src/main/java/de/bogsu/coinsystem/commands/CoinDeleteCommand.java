package de.bogsu.coinsystem.commands;

import de.bogsu.coinsystem.Coinsystem;
import de.bogsu.coinsystem.database.DataBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CoinDeleteCommand - Diese Klasse bereitet einen Befehl vor, mit welchem der Benutzer ein bestehendes Konto löschen kann
 */
public class CoinDeleteCommand implements CommandExecutor {

    /**
     * Methode, welche ausgeführt wird sobald der Benutzer den coindelete-Command eingegeben hat
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            Coinsystem.INSTANCE.log("Du bist kein Spieler.");
            return true;
        }

        Player player  = (Player) sender;

        DataBase db = new DataBase();

        if(db.selectCredit(args[0], player.getName()) == 0){
            try{
                db.deleteAccount(args[0], player.getName());
                player.sendMessage("Konto wurde gelöscht");
            } catch  (Exception e) {
                player.sendMessage("Konto konnte nicht gelöscht werden");
            }
        } else if(db.selectCredit(args[0], player.getName()) > 0){
            player.sendMessage("Das Konto kann nicht gelöscht werden solange noch Guthaben vorhanden ist.");
        } else {
            player.sendMessage("Du besitzt kein Konto mit dem angegebenen Namen.");
        }

        return true;
    }
}

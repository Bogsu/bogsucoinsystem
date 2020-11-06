package de.bogsu.coinsystem.listener;

import de.bogsu.coinsystem.database.DataBase;
import de.bogsu.coinsystem.libaries.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.Objects;

/**
 * CoinManagementListener - Diese Klasse ist für die Interaktion mit dem CustomGUI zuständig
 */
public class CoinManagementListener implements Listener {

    private final Inventory mangementInventory;

    /**
     * Methode um ein neues Inventar zu erstellen und einen Verweis auf die managementInventory-Variable zu kreieren
     */
    public CoinManagementListener() {
        this.mangementInventory = Bukkit.createInventory(null, 9, "§5 Coinsystem §8| §7 Management");
    }

    /**
     * Methode um sicherzustellen, dass das neue Inventar nur geöffnet wird wenn sich ein Gold Nugget in der Hand befindet
     */
    @EventHandler
    private void onInteract(PlayerInteractEvent  event){
        if(event.getItem() == null) return;
        if(event.getItem().getType() == Material.WHITE_BED){
            event.getPlayer().openInventory(this.mangementInventory);
            event.setCancelled(true);
        }
    }

    /**
     * Methode um das Verhalten des Plugins zu steuern, je nachdem welches Item angeklickt wurde
     */
    @EventHandler
    private void onItemClick(InventoryClickEvent event){
        prepareInventory(event);
    }

    /**
     * Methode um jedes Konto des Benutzers auszulesen und für das Anzeigen im Inventar vorzubereiten.
     * - Jedes Konto wird als einzelnes Item angezeigt
     * - Eine Materialliste vereinfacht das setzen der Items, welche zu den jeweiligen Konten gehören
     * - Je nachdem welches Material angeklickt wurde, wird ein extra Prozess ausgeführt
     */
    public void prepareInventory(InventoryClickEvent event){
        DataBase db = new DataBase();
        String[] accounts = db.selectAccounts(event.getWhoClicked().getName()).split(", ");
        int[] credits  = new int[accounts.length];
        for(String a : accounts){
            int i = 0;
            credits[i] = db.selectCredit(a, event.getWhoClicked().getName());
            i++;
        }

        int j = 0;
        Material[] materiallist = {Material.DIAMOND, Material.GOLD_INGOT, Material.EMERALD, Material.IRON_INGOT, Material.COAL};

        for(String a : accounts){
            this.mangementInventory.setItem(j, new ItemBuilder(materiallist[j])
                    .displayname(a)
                    .lore("Guthaben: "+db.selectCredit(a, event.getWhoClicked().getName())+" Coins")
                    .flag(ItemFlag.HIDE_ATTRIBUTES)
                    .build());
            j++;
        }

        event.setCancelled(true);

        if(event.getCurrentItem() == null){
            return;
        }

        int k=0;
        String activeAccount = null;
        int currentCredit = 0;

        for(Material m : materiallist){
            if(m == event.getCurrentItem().getType()){
                activeAccount = accounts[k];
                currentCredit = db.selectCredit(activeAccount, event.getWhoClicked().getName());
                event.getWhoClicked().sendMessage(m.toString());
                this.mangementInventory.setItem(7, new ItemBuilder(Material.GREEN_WOOL)
                        .displayname("1 Coin einzahlen")
                        .lore("In folgendes Konto: "+accounts[j])
                        .flag(ItemFlag.HIDE_ATTRIBUTES)
                        .build());
                this.mangementInventory.setItem(8, new ItemBuilder(Material.RED_WOOL)
                        .displayname("1 Coin auszahlen")
                        .lore("Aus folgendem Konto: "+accounts[j])
                        .flag(ItemFlag.HIDE_ATTRIBUTES)
                        .build());
            }
            j++;
        }

        if(Objects.equals(event.getClickedInventory(), this.mangementInventory)){
            if(event.getCurrentItem().getType() == Material.GREEN_WOOL){
                currentCredit++;
                db.updateCredit(activeAccount, event.getWhoClicked().getName(), currentCredit);
                event.getWhoClicked().sendMessage("Coin wurde eingezahlt");
                this.mangementInventory.clear();
            } else if (event.getCurrentItem().getType() == Material.RED_WOOL){
                currentCredit--;
                db.updateCredit(activeAccount, event.getWhoClicked().getName(), currentCredit);
                event.getWhoClicked().sendMessage("Coin wurde ausgezahlt");
                this.mangementInventory.clear();
            }
        }
    }
}

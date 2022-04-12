package cypher.itemdisenchanter.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class DisenchantListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory eInv = e.getClickedInventory();
        if(eInv == null) return;
        if(eInv.getType()!= InventoryType.SMITHING) return;
        if(eInv.getItem(2)==null) return;
        if(e.getSlot()==2 && eInv.getItem(2).getType()==Material.ENCHANTED_BOOK){
            //Because the Recipe isn't known, we have to set all slots manually.
            //There is an option to add new recipes but sadly they dont take "any enchanted item" as input
            e.getWhoClicked().setItemOnCursor(eInv.getItem(2));
            ItemStack books = eInv.getItem(1);
            books.setAmount(books.getAmount()-1);
            eInv.setItem(2, new ItemStack(Material.AIR));
            eInv.setItem(0, buildDisenchantedItem(eInv.getItem(0)));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareSmithing(PrepareSmithingEvent e){
        Inventory eInv = e.getInventory();

        if(eInv == null) return;
        if(eInv.getType()!= InventoryType.SMITHING) return;
        if(eInv.getItem(0)==null || eInv.getItem(1)==null) return;

        if(eInv.getItem(0).getEnchantments().size()>0 && eInv.getItem(1).getType()==Material.BOOK){
            e.setResult(buildEnchantedBook(eInv.getItem(0)));
        }
    }

    public ItemStack buildDisenchantedItem(ItemStack item){
        ItemStack disenchantedItem = item.clone();

        if(disenchantedItem.getEnchantments().size()>0){
            ItemMeta iMeta = disenchantedItem.getItemMeta();
            for(Enchantment e : disenchantedItem.getEnchantments().keySet()){
                iMeta.removeEnchant(e);
            }
            disenchantedItem.setItemMeta(iMeta);
        }
        return disenchantedItem;
    }

    public ItemStack buildEnchantedBook(ItemStack basedOnItem){
        ItemStack enchantedItem = basedOnItem.clone();
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) book.getItemMeta();

        if(enchantedItem.getEnchantments().size()>0){
            for(Enchantment e : enchantedItem.getEnchantments().keySet()){
                bookmeta.addStoredEnchant(e, enchantedItem.getEnchantments().get(e), true);
            }
            book.setItemMeta(bookmeta);
        }

        return book;
    }
}

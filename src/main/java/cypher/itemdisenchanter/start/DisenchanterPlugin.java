package cypher.itemdisenchanter.start;

import cypher.itemdisenchanter.listener.DisenchantListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DisenchanterPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        initListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void initListeners(){
        Bukkit.getServer().getPluginManager().registerEvents(new DisenchantListener(), this);
    }
}

package fr.tealwen.auth.Listener;

import fr.tealwen.auth.AuthPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static fr.tealwen.auth.AuthPlugin.sqlLib;


public class LeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){

        String uid = event.getPlayer().getUniqueId().toString();
       sqlLib.getDatabase("Auth").executeStatement("UPDATE account SET status = '0' WHERE uid="+ "\""+uid+"\"" + ";");

    }


}

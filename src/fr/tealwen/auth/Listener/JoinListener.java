package fr.tealwen.auth.Listener;

import fr.tealwen.auth.AuthPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public  void onPlayerJoin(PlayerJoinEvent event){

        Player player     = event.getPlayer();
        String uid        = player.getUniqueId().toString();
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_WHITE = "\u001B[37m";
        String password;
        String uidBase     = (String)AuthPlugin.sqlLib.getDatabase("Auth").queryValue("SELECT uid FROM account WHERE uid="+"\""+uid+"\";", "uid");

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2000000, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 2000000, true));
        player.setSilent(true);

        if (uid.equals(uidBase)){

            player.sendMessage("§4§l[Auth] : §7§lVeuillez vous identifier en utilisant la commande /login <mdp>");

            password = (String)AuthPlugin.sqlLib.getDatabase("Auth").queryValue("select mdp FROM account where uid=" + "\"" +uid + "\";", "mdp");

            System.out.println(ANSI_GREEN + "==========[ Auth - Debug ] ==========");
            System.out.println("Player Name : " + player.getName());
            System.out.println("Player UID  : " + uid);
            System.out.println("Player Password : " + password);

        } else {

            player.sendMessage("§4§l[Auth] : §7§lVeuillez vous identifier en utilisant la commande /register <mdp>");
            System.out.println(ANSI_GREEN + "==========[ Auth - Debug ] ==========");
            System.out.println("Player Name : " + player.getName());
            System.out.println("Player UID  : " + uid);
            System.out.println("Player Password : pas encore inscrit");

        }
        System.out.println(ANSI_GREEN + "=====================================" + ANSI_WHITE);


    }
}

package fr.tealwen.auth.Commands;

import fr.tealwen.auth.AuthPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;


import static fr.tealwen.auth.utils.hashing.*;

public class register implements CommandExecutor {


    private AuthPlugin main;

    public register(AuthPlugin authPlugin){
        this.main = authPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player player   = (Player)commandSender;
            String uid      = player.getUniqueId().toString();
            String uidBase  = (String)AuthPlugin.sqlLib.getDatabase("Auth").queryValue("SELECT uid FROM account WHERE uid="+"\""+uid+"\";", "uid");
            String securePassword;

            if (args.length == 0){
                player.sendMessage("§4§l[Auth - Error] : " + main.getConfig().getString("message.RegisterCommandMessage"));
            } else {

                if(!uid.equals(uidBase)){

                    securePassword = sha512(args[0]);

                    if(AuthPlugin.sqlLib.getDatabase("Auth").executeStatement("INSERT INTO account(id , uid , mdp, status) " +
                            "VALUES(NULL," + "\"" + player.getUniqueId().toString() + "\""+ "," + "\"" + securePassword + "\"" +",1);")){

                        player.sendMessage("§4§l[Auth] : " + main.getConfig().getString("message.AccountCreatedSucces"));

                        for (PotionEffect effect : player.getActivePotionEffects())
                            player.removePotionEffect(effect.getType());

                    } else {

                        player.sendMessage("§4§l[Auth - Error] " + main.getConfig().getString("message.SqlError"));

                    }
                } else {

                    player.sendMessage("§4§l[Auth] : " + main.getConfig().getString("message.AccountAlreadyExist"));
                    return false;

                }

            }

        }
        return false;
    }
}

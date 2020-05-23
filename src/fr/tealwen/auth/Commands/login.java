package fr.tealwen.auth.Commands;

import fr.tealwen.auth.AuthPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import static fr.tealwen.auth.AuthPlugin.sqlLib;
import static fr.tealwen.auth.utils.hashing.*;


public class login implements CommandExecutor {

    private AuthPlugin main;

    public login(AuthPlugin authPlugin){
        this.main = authPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {

            Player player       = (Player) commandSender;
            String uid          = player.getUniqueId().toString();
            String passwordBase = (String) AuthPlugin.sqlLib.getDatabase("Auth").queryValue("SELECT mdp FROM account WHERE uid=" + "\"" + uid + "\";", "mdp");
            int status = (int) AuthPlugin.sqlLib.getDatabase("Auth").queryValue("SELECT status FROM account WHERE uid=" + "\"" + uid + "\";", "status");

            if (args.length == 0) {
                player.sendMessage("§4§l[Auth - Error] : "+ main.getConfig().getString("message.LoginCommandMessage"));
            }else {


                if (status == 0) {

                    String passwordHash = sha512(args[0]);

                    if (passwordHash.equals(passwordBase)) {


                        player.sendMessage("§4§l[Auth] : "+ main.getConfig().getString("message.LoginInSucces"));

                        sqlLib.getDatabase("Auth").executeStatement("UPDATE account SET status = '1' WHERE uid=" + "\"" + uid + "\"" + ";");

                        for (PotionEffect effect : player.getActivePotionEffects())
                            player.removePotionEffect(effect.getType());

                    } else {

                        player.sendMessage("§4§l[Auth] : " + main.getConfig().getString("message.WrongPassword"));
                        return false;

                    }

                } else {

                    player.sendMessage("§4§l[Auth] : " + main.getConfig().getString("message.AlreadyConnectedMessage"));
                }

            }

        }

        return false;
    }
}


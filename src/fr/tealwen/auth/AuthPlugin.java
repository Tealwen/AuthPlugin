package fr.tealwen.auth;

import com.pablo67340.SQLiteLib.Main.SQLiteLib;
import fr.tealwen.auth.Commands.login;
import fr.tealwen.auth.Commands.register;
import fr.tealwen.auth.Listener.JoinListener;
import fr.tealwen.auth.Listener.LeaveListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin {

    public static SQLiteLib sqlLib;
    public FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        System.out.println("[Auth-Plugin] - Plugin Enabled | Auth By Droopi29");

        saveDefaultConfig();

        sqlLib = SQLiteLib.hookSQLiteLib(this);
        sqlLib.initializeDatabase(this,"Auth", "CREATE TABLE IF NOT EXISTS account(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uid TEXT NOT NULL," +
                "mdp TEXT NOT NULL," +
                "status INTEGER NOT NULL);" );

        //commandes
        this.getCommand("register").setExecutor(new register(this));
        this.getCommand("login").setExecutor(new login(this));

        //Listener
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

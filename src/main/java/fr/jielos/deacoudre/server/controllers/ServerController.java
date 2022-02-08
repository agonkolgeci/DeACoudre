package fr.jielos.deacoudre.server.controllers;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class ServerController {

    public final JavaPlugin instance;
    public ServerController(JavaPlugin instance) {
        this.instance = instance;
    }

    abstract public void register();

}

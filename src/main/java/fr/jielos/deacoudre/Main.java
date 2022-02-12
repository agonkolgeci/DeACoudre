package fr.jielos.deacoudre;

import fr.jielos.deacoudre.server.controllers.CommandsController;
import fr.jielos.deacoudre.server.controllers.ListenersController;
import fr.jielos.deacoudre.game.Game;
import fr.minuskube.netherboard.Netherboard;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static Main instance;

    static Game game;

    @Override
    public void onEnable() {
        instance = this;

        game = new Game(this);
        game.init();

        saveDefaultConfig();

        new ListenersController(this).register();
        new CommandsController(this).register();
    }

    @Override
    public void onDisable() {
        game.getScoreboardController().deleteBoards();
    }

    public static Main getInstance() {
        return instance;
    }
    public static Game getGame() {
        return game;
    }
}

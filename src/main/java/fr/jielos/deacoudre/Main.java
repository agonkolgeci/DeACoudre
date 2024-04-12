package fr.jielos.deacoudre;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.server.controllers.CommandsController;
import fr.jielos.deacoudre.server.controllers.ListenersController;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    static Main instance;

    static Game game;

    @Override
    public void onEnable() {
        instance = this;

        game = new Game(this);
        game.init();

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

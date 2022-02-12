package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.data.PlayerBoard;
import org.bukkit.entity.Player;

public class ScoreboardController extends GameController {

    public ScoreboardController(Game game) {
        super(game);
    }

    public void updateBoard(final Player player) {
        getPlayerBoard(player).update();
    }

    public void updateBoards() {
        for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
            updateBoard(player);
        }
    }

    public void deleteBoard(final Player player) {
        if(hasPlayerBoard(player)) {
            getPlayerBoard(player).destroy();
        }
    }

    public void deleteBoards() {
        for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
            deleteBoard(player);
        }
    }

    public boolean hasPlayerBoard(final Player player) {
        return game.getGameData().getPlayersBoards().containsKey(player);
    }

    public PlayerBoard getPlayerBoard(final Player player) {
        if(hasPlayerBoard(player)) {
            return game.getGameData().getPlayersBoards().get(player);
        }

        return new PlayerBoard(game, player);
    }
}

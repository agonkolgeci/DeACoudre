package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.data.GamePlayer;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Message;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class JumpsController extends GameComponent {

    int gameJumperIndex;
    Player gameJumper;

    public JumpsController(Game game) {
        super(game);

        this.gameJumperIndex = -1;
    }

    public void nextJump() {
        if(game.getGameData().getPlayers().size() > 1) {
            // 4 PLAYERS > -1+1=0 SO index = 0 - true
            // 4 PLAYERS > 0+1=1 SO index = 1 - true
            // 4 PLAYERS > 1+1=2 SO index = 2 - true
            // 4 PLAYERS > 2+1=3 SO index = 3 - true
            // 4 PLAYERS > 4 XXXXX so index = 0 - true

            // index = 1 --> 2ème joueur = Z1
            // index = 1

            if(game.getGameData().getPlayers().size() > (gameJumperIndex+1)) {
                gameJumperIndex++;
            } else {
                gameJumperIndex = 0;
            }

            final Player gameJumper = game.getGameData().getPlayers().get(gameJumperIndex);

            if(game.getGameData().isGamePlayer(gameJumper)) {
                final GamePlayer gamePlayerJumper = game.getGameData().getGamePlayer(gameJumper);

                setGameJumper(gameJumper);

                gameJumper.teleport(game.getConfigController().getAsLocation(Config.JUMP));
                gameJumper.setGameMode(GameMode.ADVENTURE);

                gamePlayerJumper.scheduleJump();

                game.getScoreboardController().updateBoards();
                game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_GO_JUMP.getValue(), gameJumper.getName()));
            }
        } else {
            game.checkEnd();
        }
    }

    public boolean checkIfNextJump(final Player player) {
        if(isGameJumper(player)) {
            decreaseGameJumperIndex();
            nextJump();

            return true;
        }

        return false;
    }

    public void decreaseGameJumperIndex() {
        gameJumperIndex--;
    }

    public boolean isGameJumper(final Player player) {
        return gameJumper == player;
    }

    public void setGameJumper(Player gameJumper) {
        this.gameJumper = gameJumper;
    }
    public Player getGameJumper() {
        return gameJumper;
    }
}

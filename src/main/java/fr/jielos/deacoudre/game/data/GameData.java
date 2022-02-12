package fr.jielos.deacoudre.game.data;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData extends GameComponent {

    final List<Player> players;
    final List<Player> spectators;

    final Map<Player, PlayerBoard> playersBoards;

    final Map<Player, GamePlayer> gamePlayers;

    Player gameWinner;

    public GameData(Game game) {
        super(game);

        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();

        this.playersBoards = new HashMap<>();

        this.gamePlayers = new HashMap<>();
    }

    public List<Player> getPlayers() {
        return players;
    }
    public List<Player> getSpectators() {
        return spectators;
    }

    public Map<Player, PlayerBoard> getPlayersBoards() {
        return playersBoards;
    }

    public Map<Player, GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public boolean isGamePlayer(final Player player) {
        return gamePlayers.containsKey(player);
    }

    public GamePlayer getGamePlayer(final Player player) {
        if(isGamePlayer(player)) {
            return gamePlayers.get(player);
        }

        return null;
    }

    public void updateGamePlayer(final Player player, final GamePlayer gamePlayer) {
        gamePlayers.replace(player, gamePlayer);
    }

    public void setGameWinner(Player gameWinner) {
        this.gameWinner = gameWinner;
    }
    public Player getGameWinner() {
        return gameWinner;
    }
}

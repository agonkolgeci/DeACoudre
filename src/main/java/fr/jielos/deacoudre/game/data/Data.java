package fr.jielos.deacoudre.game.data;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.schedulers.PlayerJump;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data extends GameComponent {

    final List<Player> players;
    final List<Player> spectators;

    final Map<Player, Attribute> attributes;
    final Map<Player, PlayerJump> jumps;

    int indexJump;
    Player playerJump;

    public Data(Game game) {
        super(game);

        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();

        this.attributes = new HashMap<>();
        this.jumps = new HashMap<>();

        this.indexJump = -1;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public List<Player> getSpectators() {
        return spectators;
    }

    public Map<Player, Attribute> getAttributes() {
        return attributes;
    }
    public Map<Player, PlayerJump> getJumps() {
        return jumps;
    }

    public int getIndexJump() {
        return indexJump;
    }
    public void incrementIndexJump() {
        if(players.size() > indexJump+1) {
            indexJump++;
        } else {
            indexJump=0;
        }
    }

    public void setPlayerJump(Player playerJump) {
        this.playerJump = playerJump;
    }
    public Player getPlayerJump() {
        return playerJump;
    }
}

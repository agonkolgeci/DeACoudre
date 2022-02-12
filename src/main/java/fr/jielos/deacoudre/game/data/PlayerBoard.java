package fr.jielos.deacoudre.game.data;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Status;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerBoard extends GameComponent {

    final Player player;
    final BPlayerBoard board;
    public PlayerBoard(final Game game, final Player player) {
        super(game);

        this.player = player;
        this.board = Netherboard.instance().createBoard(player, game.getScoreboard(), game.getConfigController().getAsString(Config.SCOREBOARD_TITLE));

        game.getGameData().getPlayersBoards().put(player, this);
    }

    public void update() {
        final String NA = "§cN/A";

        final List<String> lines = new ArrayList<>(Arrays.asList(game.getStatus().getScoreboardLines().clone()));
        final Map<String, String> remplacements = new HashMap<>();

        remplacements.put("{players_size}", String.valueOf(game.getGameData().getPlayers().size()));
        remplacements.put("{players_missing}", String.valueOf(game.getConfigController().getAsInteger(Config.MIN_PLAYERS) - game.getGameData().getPlayers().size()));
        remplacements.put("{map_name}", game.getConfigController().getAsString(Config.MAP_NAME));

        remplacements.put("{timer_launch}", game.isLaunching() ? String.valueOf(game.getLaunchScheduler().getSeconds()) : NA);

        remplacements.put("{healths}", game.getGameData().isGamePlayer(player) ? String.valueOf(game.getGameData().getGamePlayer(player).getHealths()) : NA);
        remplacements.put("{health_limit}", game.getGameData().isGamePlayer(player) ? (game.getConfigController().getAsBoolean(Config.HEALTHS_LIMITED) ? String.format("(%d max.)", game.getConfigController().getAsInteger(Config.HEALTHS_LIMIT)) : "") : "");
        remplacements.put("{middles}", game.getGameData().isGamePlayer(player) ? String.valueOf(game.getGameData().getGamePlayer(player).getMiddles()) : NA);
        remplacements.put("{success_jumps}", game.getGameData().isGamePlayer(player) ? String.valueOf(game.getGameData().getGamePlayer(player).getSuccessJumps()) : NA);
        remplacements.put("{failed_jumps}", game.getGameData().isGamePlayer(player) ? String.valueOf(game.getGameData().getGamePlayer(player).getFailedJumps()) : NA);

        remplacements.put("{game_jumper}", game.getJumpsController().getGameJumper() != null ? game.getJumpsController().getGameJumper().getName() : NA);
        remplacements.put("{game_winner}", game.getGameData().getGameWinner() != null ? game.getGameData().getGameWinner().getName() : NA);

        for(final Map.Entry<String, String> remplacement : remplacements.entrySet()) {
            lines.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line.replace(remplacement.getKey(), remplacement.getValue())));
        }

        if(game.getConfigController().getAsBoolean(Config.SCOREBOARD_FOOTER_ENABLED)) {
            lines.addAll(Arrays.asList("§k", game.getConfigController().getAsString(Config.SCOREBOARD_FOOTER_CONTENT)));
        }

        board.setAll(lines.toArray(new String[0]));
    }

    public void destroy() {
        board.delete();

        game.getGameData().getPlayersBoards().remove(player);
    }
}

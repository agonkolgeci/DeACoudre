package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Status;
import fr.jielos.deacoudre.game.references.Message;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        event.setJoinMessage(null);

        game.getGameController().clearContents(player);

        if(!game.getGameData().getPlayers().contains(player)) {
            game.getGameController().addPlayer(player);

            if(game.getStatus() == Status.WAIT_FOR_PLAYERS || game.getStatus() == Status.STARTING) {
                player.setGameMode(GameMode.ADVENTURE);

                event.setJoinMessage(String.format(Message.PLAYER_JOIN.getValue(), player.getName(), game.getGameData().getPlayers().size(), game.getConfigController().getAsInteger(Config.MAX_PLAYERS)));

                game.getScoreboardController().updateBoards();

                if(game.getStatus() == Status.WAIT_FOR_PLAYERS) {
                    game.checkLaunch();
                }
            } else {
                game.getGameController().addSpectator(player);
            }
        }

        player.teleport(game.getConfigController().getAsLocation(Config.WAITING_ROOM));
    }

}

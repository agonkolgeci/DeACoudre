package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Status;
import fr.jielos.deacoudre.game.references.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        event.setQuitMessage(null);

        if(game.getGameData().getPlayers().contains(player)) {
            game.getGameData().getPlayers().remove(player);

            if(game.getStatus() == Status.WAIT_FOR_PLAYERS || game.getStatus() == Status.STARTING) {
                event.setQuitMessage(String.format(Message.PLAYER_QUIT.getValue(), player.getName(), game.getGameData().getPlayers().size(), game.getConfigController().getAsInteger(Config.MAX_PLAYERS)));

                if(game.getStatus() == Status.WAIT_FOR_PLAYERS) {
                    game.checkLaunch();
                } else if(game.getStatus() == Status.STARTING) {
                    if(!game.canLaunch()) {
                        if(game.isLaunching()) {
                            game.getLaunchScheduler().stop();
                        }
                    }
                }

                game.getScoreboardController().updateBoards();
            } else if(game.getStatus() == Status.IN_GAME) {
                event.setQuitMessage(String.format(Message.PLAYER_LEAVE.getValue(), player.getName()));

                if(game.getGameData().isGamePlayer(player)) {
                    game.getGameData().getGamePlayer(player).eliminate();
                }
            }
        }
    }


}

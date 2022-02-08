package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
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

        if(game.getData().getPlayers().contains(player)) {
            game.getData().getPlayers().remove(player);

            if(game.getStatus() == Status.WAIT || game.getStatus() == Status.LAUNCH) {
                event.setQuitMessage(String.format(Message.PLAYER_QUIT.getAsString(), player.getName(), game.getData().getPlayers().size(), game.getConfig().getMaxPlayers()));
            } else if(game.getStatus() == Status.PLAY) {
                event.setQuitMessage(String.format(Message.PLAYER_LEAVE.getAsString(), player.getName()));
                game.getGameController().eliminatePlayer(player);

                if(game.getData().getPlayerJump() == player) {
                    game.getGameController().nextJump();
                } else {
                    game.getGameController().checkEnd();
                }
            }
        }
    }


}

package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
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

        if(!game.getData().getPlayers().contains(player)) {
            game.getData().getPlayers().add(player);

            if(game.getStatus() == Status.WAIT || game.getStatus() == Status.LAUNCH) {
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(game.getConfig().getWaitingRoom());

                event.setJoinMessage(String.format(Message.PLAYER_JOIN.getAsString(), player.getName(), game.getData().getPlayers().size(), game.getConfig().getMaxPlayers()));

                game.getScoreboardController().updateBoards();

                if(game.getStatus() == Status.WAIT) {
                    game.getGameController().checkLaunch();
                }
            } else if(game.getStatus() == Status.PLAY) {
                game.getGameController().putSpectator(player);
                player.teleport(game.getConfig().getJump());
            }
        }
    }

}

package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.references.Status;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final Game game = Main.getGame();

        if(game.isFull()) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, Message.KICK_GAME_FULL.getValue());
        } else if(game.getStatus() == Status.END) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Message.KICK_GAME_END.getValue());
        }
    }

}

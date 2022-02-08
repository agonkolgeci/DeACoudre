package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Status;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        final Game game = Main.getGame();
        final Entity entity = event.getEntity();

        if(entity instanceof Player) {
            event.setCancelled(true);

            final Player player = (Player) entity;

            if(game.getStatus() == Status.PLAY) {
                if(game.getGameController().isPlay(player)) {
                    if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        game.getGameController().losePlayer(player);
                        game.getGameController().nextJump();
                    }
                }
            }
        }
    }

}

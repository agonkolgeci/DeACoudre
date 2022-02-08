package fr.jielos.deacoudre.game.schedulers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameScheduler;
import fr.jielos.deacoudre.game.references.Status;
import org.bukkit.entity.Player;

public class PlayerJump extends GameScheduler {

    final Player player;
    public PlayerJump(final Game game, final Player player) {
        super(game);

        this.player = player;
        this.seconds = game.getConfig().getTimerJump();
    }

    int seconds;

    @Override
    public void run() {
        if(game.getStatus() == Status.PLAY) {
            if(game.getGameController().isPlay(player)) {
                if(seconds > 0) {
                    for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
                        player.setLevel(seconds);
                    }

                    seconds--;
                } else {
                    cancel();

                    game.getGameController().losePlayer(player);
                    game.getGameController().nextJump();
                }

                return;
            }
        }

        cancel();
    }
}

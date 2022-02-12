package fr.jielos.deacoudre.game.schedulers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameScheduler;
import fr.jielos.deacoudre.game.data.GamePlayer;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Status;

public class PlayerJumpScheduler extends GameScheduler {

    final GamePlayer gamePlayer;
    public PlayerJumpScheduler(final Game game, final GamePlayer gamePlayer) {
        super(game);

        this.gamePlayer = gamePlayer;
        this.seconds = game.getConfigController().getAsInteger(Config.TIMER_JUMP);

        gamePlayer.setPlayerJumpScheduler(this);
    }

    int seconds;

    @Override
    public void run() {
        if(game.getStatus() == Status.IN_GAME) {
            if(seconds > 0) {
                gamePlayer.getPlayer().setLevel(seconds);

                seconds--;
            } else {
                delete();

                gamePlayer.lose();
            }
        }
    }

    public void delete() {
        cancel();

        gamePlayer.setPlayerJumpScheduler(null);
    }
}

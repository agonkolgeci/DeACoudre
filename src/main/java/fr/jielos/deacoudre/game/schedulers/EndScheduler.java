package fr.jielos.deacoudre.game.schedulers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameScheduler;

public class EndScheduler extends GameScheduler {

    public EndScheduler(Game game) {
        super(game);
    }

    @Override
    public void run() {
        game.getInstance().getServer().reload();
    }
}

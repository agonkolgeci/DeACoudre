package fr.jielos.deacoudre.game.schedulers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameScheduler;

public class GameEnd extends GameScheduler {

    public GameEnd(Game game) {
        super(game);
    }

    @Override
    public void run() {
        game.getInstance().getServer().reload();
    }
}

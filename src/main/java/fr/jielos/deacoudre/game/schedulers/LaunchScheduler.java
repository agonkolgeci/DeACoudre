package fr.jielos.deacoudre.game.schedulers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameScheduler;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.references.Status;
import org.bukkit.entity.Player;

public class LaunchScheduler extends GameScheduler {

    public LaunchScheduler(final Game game) {
        super(game);

        this.seconds = game.getConfigController().getAsInteger(Config.TIMER_LAUNCH);

        game.setLaunchScheduler(this);
    }

    int seconds;

    @Override
    public void run() {
        if(seconds > 0) {
            if(seconds % 10 == 0 || seconds <= 5) {
                game.getInstance().getServer().broadcastMessage(String.format(Message.STARTING_SOON.getValue(), seconds, (seconds > 1 ? "s" : "")));
            }

            for(final Player player : game.getGameData().getPlayers()) {
                player.setLevel(seconds);

                game.getScoreboardController().updateBoard(player);
            }

            seconds--;
        } else {
            cancel();

            game.start();
        }
    }

    public void stop() {
        delete();
        
        game.setStatus(Status.WAIT_FOR_PLAYERS);
        game.getInstance().getServer().broadcastMessage(Message.STARTING_CANCELED.getValue());

        for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
            player.setLevel(0);
        }

        game.getScoreboardController().updateBoards();
    }

    public void delete() {
        cancel();

        game.setLaunchScheduler(null);
    }

    public int getSeconds() {
        return seconds;
    }
}

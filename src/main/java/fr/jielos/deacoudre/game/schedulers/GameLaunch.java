package fr.jielos.deacoudre.game.schedulers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameScheduler;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.references.Status;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameLaunch extends GameScheduler {

    public GameLaunch(final Game game) {
        super(game);

        this.seconds = game.getConfig().getTimerLaunch();
    }

    int seconds;

    @Override
    public void run() {
        if(game.getGameController().canLaunch()) {
            if(seconds > 0) {
                if(seconds % 10 == 0 || seconds <= 5) {
                    game.getInstance().getServer().broadcastMessage(String.format(Message.STARTING_SOON.getAsString(), seconds, (seconds > 1 ? "s" : "")));
                }

                for(final Player player : game.getData().getPlayers()) {
                    player.setLevel(seconds);

                    final Map<String, String> remplacements = new HashMap<>();
                    remplacements.put("\\{launch_time}", String.valueOf(seconds));

                    game.getScoreboardController().updateBoard(player, remplacements);
                }

                seconds--;
            } else {
                cancel();

                game.start();
            }
        } else {
            cancel();

            game.setStatus(Status.WAIT);
            game.getInstance().getServer().broadcastMessage(Message.STARTING_CANCELED.getAsString());

            game.getScoreboardController().updateBoards();
        }
    }
}

package fr.jielos.deacoudre.game.data;

import fr.jielos.deacoudre.components.Cuboid;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Config extends GameComponent {

    final int minPlayers;
    final int maxPlayers;

    final int timerLaunch;
    final int timerJump;
    final int timerEnd;

    final Location waitingRoom;
    final Location jump;
    final Location end;

    final Cuboid pool;

    public Config(Game game) {
        super(game);

        final FileConfiguration configuration = game.getInstance().getConfig();

        minPlayers = configuration.getInt("minPlayers");
        maxPlayers = configuration.getInt("maxPlayers");

        timerLaunch = configuration.getInt("timers.launch");
        timerJump = configuration.getInt("timers.jump");
        timerEnd = configuration.getInt("timers.end");

        waitingRoom = (Location) configuration.get("locations.waitingRoom");
        jump = (Location) configuration.get("locations.jump");
        end = (Location) configuration.get("locations.end");

        pool = new Cuboid((Location) configuration.get("locations.pool.1"), (Location) configuration.get("locations.pool.2"));
    }

    public int getMinPlayers() {
        return Math.max(minPlayers, 2);
    }
    public int getMaxPlayers() {
        return (maxPlayers >= getMinPlayers() && maxPlayers <= game.getDyeColors().size() ? maxPlayers : game.getDyeColors().size());
    }

    public int getTimerLaunch() {
        return timerLaunch;
    }
    public int getTimerJump() {
        return timerJump;
    }
    public int getTimerEnd() {
        return timerEnd;
    }

    public Location getWaitingRoom() {
        return waitingRoom;
    }
    public Location getJump() {
        return jump;
    }
    public Location getEnd() {
        return end;
    }

    public Cuboid getPool() {
        return pool;
    }
}

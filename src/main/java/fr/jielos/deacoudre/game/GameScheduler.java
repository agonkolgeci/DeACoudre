package fr.jielos.deacoudre.game;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class GameScheduler extends BukkitRunnable {

    public final Game game;
    public GameScheduler(final Game game) {
        this.game = game;
    }

}

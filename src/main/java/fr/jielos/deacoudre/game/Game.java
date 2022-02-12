package fr.jielos.deacoudre.game;

import fr.jielos.deacoudre.game.controllers.*;
import fr.jielos.deacoudre.game.data.GamePlayer;
import fr.jielos.deacoudre.game.data.GameData;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.references.Status;
import fr.jielos.deacoudre.game.schedulers.EndScheduler;
import fr.jielos.deacoudre.game.schedulers.LaunchScheduler;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class Game {

    final JavaPlugin instance;

    final GameController gameController;
    final ScoreboardController scoreboardController;
    final ConfigController configController;
    final TeamsController teamsController;
    final JumpsController jumpsController;

    final GameData gameData;

    Status status;

    LaunchScheduler launchScheduler;

    public Game(final JavaPlugin instance) {
        this.instance = instance;

        this.gameController = new GameController(this);
        this.scoreboardController = new ScoreboardController(this);
        this.configController = new ConfigController(this);
        this.teamsController = new TeamsController(this);
        this.jumpsController = new JumpsController(this);

        this.gameData = new GameData(this);
    }

    public void init() {
        setStatus(Status.WAIT_FOR_PLAYERS);

        teamsController.initTeams();

        gameController.initPlayers();
        gameController.initPool();
        gameController.initWorlds();

        scoreboardController.updateBoards();

        checkLaunch();
    }

    public void launch() {
        setStatus(Status.STARTING);

        scoreboardController.updateBoards();

        this.launchScheduler = new LaunchScheduler(this);
        this.launchScheduler.runTaskTimer(instance, 0, 20);
    }

    public void start() {
        setStatus(Status.IN_GAME);

        if(launchScheduler != null) {
            launchScheduler.delete();
        }

        instance.getServer().broadcastMessage(Message.STARTING.getValue());

        final List<DyeColor> dyeColors = teamsController.getDyeColors();
        for(final Player player : gameData.getPlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(configController.getAsLocation(Config.JUMP));
            gameController.clearContents(player);

            final DyeColor dyeColor = dyeColors.get(new Random().nextInt(dyeColors.size()));
            final GamePlayer gamePlayer = gameController.addGamePlayer(player, dyeColor);

            teamsController.updateTeam(player);

            player.getInventory().setHelmet(gamePlayer.getWool().toItemStack());
            player.updateInventory();

            dyeColors.remove(dyeColor);
        }

        jumpsController.nextJump();
        scoreboardController.updateBoards();
    }

    public void end(final Player gameWinner) {
        instance.getServer().getScheduler().cancelAllTasks();

        setStatus(Status.END);

        gameData.setGameWinner(gameWinner);
        if(gameData.isGamePlayer(gameWinner)) {
            final GamePlayer gamePlayer = gameData.getGamePlayer(gameWinner);

            instance.getServer().broadcastMessage(String.format(Message.PLAYER_WIN_GAME.getValue(), gameWinner.getName(), gamePlayer.getHealths(), gamePlayer.getSuccessJumps()));
        }

        for(final Player player : instance.getServer().getOnlinePlayers()) {
            player.teleport(configController.getAsLocation(Config.END));
            player.setGameMode(gameData.getPlayers().contains(player) ? GameMode.ADVENTURE : GameMode.SPECTATOR);

            gameController.clearContents(player);
            scoreboardController.updateBoard(player);
        }

        new EndScheduler(this).runTaskLater(instance, configController.getAsInteger(Config.TIMER_END) * 20L);
    }

    public boolean isFull() {
        return gameData.getPlayers().size() >= configController.getAsInteger(Config.MAX_PLAYERS);
    }

    public boolean canLaunch() {
        return gameData.getPlayers().size() >= configController.getAsInteger(Config.MIN_PLAYERS);
    }

    public boolean isLaunching() {
        return status == Status.STARTING && launchScheduler != null;
    }

    public void checkLaunch() {
        if(canLaunch() && !isLaunching()) {
            launch();
        }
    }

    public void checkEnd() {
        if(gameData.getPlayers().size() == 1) {
            end(gameData.getPlayers().get(0));
        }
    }

    public JavaPlugin getInstance() {
        return instance;
    }
    public Scoreboard getScoreboard() {
        return instance.getServer().getScoreboardManager().getMainScoreboard();
    }

    public GameController getGameController() {
        return gameController;
    }
    public ScoreboardController getScoreboardController() {
        return scoreboardController;
    }
    public ConfigController getConfigController() {
        return configController;
    }
    public TeamsController getTeamsController() {
        return teamsController;
    }
    public JumpsController getJumpsController() {
        return jumpsController;
    }

    public GameData getGameData() {
        return gameData;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLaunchScheduler(LaunchScheduler launchScheduler) {
        this.launchScheduler = launchScheduler;
    }
    public LaunchScheduler getLaunchScheduler() {
        return launchScheduler;
    }
}

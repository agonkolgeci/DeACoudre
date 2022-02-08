package fr.jielos.deacoudre.game;

import fr.jielos.deacoudre.game.controllers.GameController;
import fr.jielos.deacoudre.game.controllers.ScoreboardController;
import fr.jielos.deacoudre.game.data.Attribute;
import fr.jielos.deacoudre.game.data.Config;
import fr.jielos.deacoudre.game.data.Data;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.references.Status;
import fr.jielos.deacoudre.game.schedulers.GameEnd;
import fr.jielos.deacoudre.game.schedulers.GameLaunch;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class Game {

    final JavaPlugin instance;

    final GameController gameController;
    final ScoreboardController scoreboardController;

    final Data data;
    final Config config;

    Status status;

    public Game(final JavaPlugin instance) {
        this.instance = instance;

        this.gameController = new GameController(this);
        this.scoreboardController = new ScoreboardController(this);

        this.data = new Data(this);
        this.config = new Config(this);

        this.status = Status.WAIT;
    }

    public void init() {
        for(final Player player : instance.getServer().getOnlinePlayers()) {
            if(!data.getPlayers().contains(player)) {
                data.getPlayers().add(player);
            }

            player.teleport(config.getWaitingRoom());
            player.setGameMode(GameMode.ADVENTURE);

            gameController.clearContents(player);
        }

        for(final Block block : config.getPool().getBlocks()) {
            block.setType(Material.WATER);
        }

        final Scoreboard scoreboard = instance.getServer().getScoreboardManager().getMainScoreboard();
        for(final Team team : scoreboard.getTeams()) {
            team.unregister();
        }

        for(final DyeColor dyeColor : getDyeColors()) {
            Team team = scoreboard.getTeam(dyeColor.name());
            if(team == null) {
                team = scoreboard.registerNewTeam(dyeColor.name());
                team.setPrefix(translateDyeColor(dyeColor).toString());
            }
        }

        gameController.checkLaunch();
        scoreboardController.updateBoards();
    }

    public void launch() {
        this.status = Status.LAUNCH;

        for(final Player player : data.getPlayers()) {
            scoreboardController.updateBoard(player);
        }

        new GameLaunch(this).runTaskTimer(instance, 0, 20);
    }

    public void start() {
        this.status = Status.PLAY;
        instance.getServer().broadcastMessage(Message.STARTING.getAsString());

        final List<DyeColor> dyeColors = getDyeColors();
        for(final Player player : data.getPlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(config.getJump());
            gameController.clearContents(player);

            final DyeColor dyeColor = dyeColors.get(new Random().nextInt(dyeColors.size()));

            final Attribute attribute = new Attribute(player, dyeColor);
            data.getAttributes().put(player, attribute);
            dyeColors.remove(dyeColor);

            final Scoreboard scoreboard = instance.getServer().getScoreboardManager().getMainScoreboard();
            final Team team = scoreboard.getTeam(dyeColor.name());
            if(team != null) {
                team.addEntry(player.getName());
            }

            player.setScoreboard(scoreboard);

            player.getInventory().setHelmet(attribute.getWool().toItemStack());
            player.updateInventory();
        }

        gameController.nextJump();
        scoreboardController.updateBoards();
    }

    public void end(final Player winner) {
        this.status = Status.END;

        instance.getServer().getScheduler().cancelAllTasks();

        final Attribute attribute = data.getAttributes().get(winner);
        instance.getServer().broadcastMessage(String.format(Message.PLAYER_WIN_GAME.getAsString(), winner.getName(), attribute.getHealths(), attribute.getJumps()));

        for(final Player player : instance.getServer().getOnlinePlayers()) {
            player.teleport(config.getEnd());
            player.setGameMode(GameMode.ADVENTURE);
            player.setLevel(0); player.setExp(0);

            final Map<String, String> remplacements = new HashMap<>();
            remplacements.put("\\{winner_name}", winner.getName());

            scoreboardController.updateBoard(player, remplacements);
        }

        new GameEnd(this).runTaskLater(instance, config.getTimerEnd()*20);
    }

    public List<DyeColor> getDyeColors() {
        final List<DyeColor> dyeColors = new ArrayList<>(Arrays.asList(DyeColor.values()));
        dyeColors.remove(DyeColor.BROWN);
        dyeColors.remove(DyeColor.MAGENTA);

        return dyeColors;
    }

    public ChatColor translateDyeColor(final DyeColor dyeColor) {
        switch (dyeColor) {
            case RED: {
                return ChatColor.RED;
            }

            case BLUE: {
                return ChatColor.DARK_BLUE;
            }

            case CYAN: {
                return ChatColor.DARK_AQUA;
            }

            case GRAY: {
                return ChatColor.DARK_GRAY;
            }

            case LIME: {
                return ChatColor.GREEN;
            }

            case PINK: {
                return ChatColor.LIGHT_PURPLE;
            }

            case BLACK: {
                return ChatColor.BLACK;
            }

            case GREEN: {
                return ChatColor.DARK_GREEN;
            }

            case WHITE: {
                return ChatColor.WHITE;
            }

            case ORANGE: {
                return ChatColor.GOLD;
            }

            case PURPLE: {
                return ChatColor.DARK_PURPLE;
            }

            case SILVER: {
                return ChatColor.GRAY;
            }

            case YELLOW: {
                return ChatColor.YELLOW;
            }

            case LIGHT_BLUE: {
                return ChatColor.AQUA;
            }
        }

        return null;
    }

    public JavaPlugin getInstance() {
        return instance;
    }

    public GameController getGameController() {
        return gameController;
    }
    public ScoreboardController getScoreboardController() {
        return scoreboardController;
    }

    public Data getData() {
        return data;
    }
    public Config getConfig() {
        return config;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}

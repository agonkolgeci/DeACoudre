package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.data.GamePlayer;
import fr.jielos.deacoudre.game.references.GameTeam;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamsController extends GameComponent {

    public TeamsController(Game game) {
        super(game);
    }

    public void initTeams() {
        game.getInstance().getLogger().info("Initialization of the different teams (colors, player and spectator)");

        for(final Team team : game.getScoreboard().getTeams()) {
            team.unregister();
        }

        for(final DyeColor dyeColor : getDyeColors()) {
            registerTeam(translateDyeColorToName(dyeColor), translateDyeColorToChatColor(dyeColor).toString());
        }

        for(final GameTeam gameTeam : GameTeam.values()) {
            registerTeam(gameTeam.getName(), gameTeam.getPrefix());
        }
    }

    public void updateTeam(final Player player) {
        final Team team = getTeam(player);

        if(team != null) {
            team.addEntry(player.getName());
        }

        player.setScoreboard(game.getScoreboard());
    }

    public Team getTeam(final Player player) {
        Team team;

        if(game.getGameData().isGamePlayer(player)) {
            final GamePlayer gamePlayer = game.getGameData().getGamePlayer(player);
            final DyeColor dyeColor = gamePlayer.getDyeColor();

            team = registerTeam(translateDyeColorToName(dyeColor), translateDyeColorToChatColor(dyeColor).toString());
        } else if(game.getGameData().getPlayers().contains(player)) {
            team = registerTeam(GameTeam.PLAYER.getName(), GameTeam.PLAYER.getPrefix());
        } else {
            team = registerTeam(GameTeam.SPECTATOR.getName(), GameTeam.SPECTATOR.getPrefix());
        }

        return team;
    }

    public Team registerTeam(final String name, final String prefix) {
        Team team = game.getScoreboard().getTeam(name);
        if(team == null) {
            team = game.getScoreboard().registerNewTeam(name);
        }

        team.setPrefix(prefix);

        return team;
    }

    public List<DyeColor> getDyeColors() {
        final List<DyeColor> dyeColors = new ArrayList<>(Arrays.asList(DyeColor.values()));

        dyeColors.remove(DyeColor.BROWN);
        dyeColors.remove(DyeColor.MAGENTA);

        return dyeColors;
    }

    public ChatColor translateDyeColorToChatColor(final DyeColor dyeColor) {
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

    public String translateDyeColorToName(final DyeColor dyeColor) {
        return String.format("%s_%s", GameTeam.PLAYER.getName().charAt(0), dyeColor.name());
    }
}

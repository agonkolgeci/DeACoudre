package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.data.GamePlayer;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.schedulers.PlayerJumpScheduler;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class GameController extends GameComponent {

    public GameController(Game game) {
        super(game);
    }

    public void initPlayers() {
        game.getInstance().getLogger().info("Initialization of the players connected to the server in the new game.");

        for(final Player player : game.getInstance().getServer().getOnlinePlayers()) {
            addPlayer(player);

            player.teleport(game.getConfigController().getAsLocation(Config.WAITING_ROOM));

            game.getTeamsController().updateTeam(player);
        }
    }

    public void initPool() {
        game.getInstance().getLogger().info("Initialization of the water pool: filling it with water.");

        for(final Block block : game.getConfigController().getAsArea(Config.POOL).getBlocks()) {
            block.setType(Material.WATER);
        }
    }

    public void initWorlds() {
        for(final World world : game.getInstance().getServer().getWorlds()) {
            world.setGameRuleValue("doDaylightCycle", String.valueOf(false));
            world.setTime(6000);
        }
    }

    public void addPlayer(final Player player) {
        if(!game.getGameData().getPlayers().contains(player)) {
            game.getGameData().getPlayers().add(player);
        }

        player.setGameMode(GameMode.ADVENTURE);

        clearContents(player);

        game.getTeamsController().updateTeam(player);
        game.getScoreboardController().updateBoard(player);
    }

    public void addSpectator(final Player player) {
        game.getGameData().getPlayers().remove(player);

        if(!game.getGameData().getSpectators().contains(player)) {
            game.getGameData().getSpectators().add(player);
        }

        player.setGameMode(GameMode.SPECTATOR);

        clearContents(player);

        game.getTeamsController().updateTeam(player);
        game.getScoreboardController().updateBoard(player);
    }

    public GamePlayer addGamePlayer(final Player player, final DyeColor dyeColor) {
        if(!game.getGameData().isGamePlayer(player)) {
            return new GamePlayer(game, player, dyeColor);
        }

        return game.getGameData().getGamePlayer(player);
    }

    public void clearContents(final Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setLevel(0); player.setExp(0);
        player.setHealth(20L); player.setFoodLevel(20);

        for(final PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }
}

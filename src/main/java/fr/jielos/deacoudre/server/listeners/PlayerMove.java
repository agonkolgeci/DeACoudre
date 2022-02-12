package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.data.GamePlayer;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.references.Status;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        if(game.getStatus() == Status.IN_GAME) {
            if(game.getGameData().isGamePlayer(player)) {
                final GamePlayer gamePlayer = game.getGameData().getGamePlayer(player);

                if(game.getJumpsController().isGameJumper(player) && game.getConfigController().getAsArea(Config.POOL).isIn(player)) {
                    final Block block = player.getLocation().getBlock();

                    BlockState blockState;

                    if(!block.getRelative(BlockFace.NORTH).isLiquid() && !block.getRelative(BlockFace.SOUTH).isLiquid() && !block.getRelative(BlockFace.EAST).isLiquid() && !block.getRelative(BlockFace.WEST).isLiquid()) {
                        if(gamePlayer.increaseHealth()) {
                            game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_WIN_HEALTH.getValue(), player.getName()));
                        } else {
                            game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_SUCCESSFUL_MIDDLE.getValue(), player.getName()));
                        }

                        gamePlayer.increaseMiddle();

                        block.setType(Material.STAINED_GLASS);

                        blockState = block.getState();
                        blockState.setData(gamePlayer.getStainedGlass().getData());
                    } else {
                        block.setType(Material.WOOL);

                        blockState = block.getState();
                        blockState.setData(gamePlayer.getWool());

                        game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_SUCCESSFUL_JUMP.getValue(), player.getName()));
                    }

                    blockState.update();

                    gamePlayer.increaseSuccessJump();
                    gamePlayer.stopSchedulerJump();

                    game.getGameData().updateGamePlayer(player, gamePlayer);

                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(game.getConfigController().getAsLocation(Config.JUMP));

                    game.getJumpsController().nextJump();
                }
            }
        }
    }

}

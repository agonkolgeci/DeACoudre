package fr.jielos.deacoudre.server.listeners;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Status;
import fr.jielos.deacoudre.game.data.Attribute;
import fr.jielos.deacoudre.game.references.Message;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Game game = Main.getGame();
        final Player player = event.getPlayer();

        if(game.getStatus() == Status.PLAY) {
            if(game.getGameController().isPlay(player)) {
                if(game.getData().getPlayerJump() == player && game.getConfig().getPool().isIn(player)) {
                    final Attribute attribute = game.getData().getAttributes().get(player);
                    final Block block = player.getLocation().getBlock();

                    // En cas de dé à coudre :
                    if(!block.getRelative(BlockFace.NORTH).isLiquid() && !block.getRelative(BlockFace.SOUTH).isLiquid() && !block.getRelative(BlockFace.EAST).isLiquid() && !block.getRelative(BlockFace.WEST).isLiquid()) {
                        attribute.increaseHealth();
                        game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_WIN_HEALTH.getAsString(), player.getName()));
                    } else {
                        game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_SUCCESSFUL_JUMP.getAsString(), player.getName()));
                    }

                    attribute.increaseJump();
                    game.getGameController().removeJump(player);

                    block.setType(Material.WOOL);

                    final BlockState blockState = block.getState();
                    blockState.setData(attribute.getWool());
                    blockState.update();

                    game.getData().getAttributes().replace(player, attribute);

                    player.setGameMode(GameMode.SPECTATOR);
                    player.teleport(game.getConfig().getJump());

                    game.getGameController().nextJump();
                }
            }
        }
    }

}

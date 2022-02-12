package fr.jielos.deacoudre.game.data;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Config;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.schedulers.PlayerJumpScheduler;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class GamePlayer {

    final Game game;
    final Player player;
    final DyeColor dyeColor;

    int healths;
    int middles;
    int successJumps;
    int failedJumps;

    PlayerJumpScheduler playerJumpScheduler;

    public GamePlayer(final Game game, final Player player, final DyeColor dyeColor) {
        this.game = game;
        this.player = player;
        this.dyeColor = dyeColor;

        this.healths = 0;
        this.middles = 0;
        this.successJumps = 0;
        this.failedJumps = 0;

        game.getGameData().getGamePlayers().put(player, this);
    }

    public void lose() {
        increaseFailedJump();
        stopSchedulerJump();

        if(healths >= 1) {
            decreaseHealth();

            game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_LOSE_HEALTH.getValue(), player.getName(), healths));

            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(game.getConfigController().getAsLocation(Config.JUMP));

            game.getJumpsController().nextJump();
        } else {
            eliminate();
        }
    }

    public void eliminate() {
        stopSchedulerJump();

        game.getGameController().addSpectator(player);
        game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_ELIMINATE.getValue(), player.getName()));

        if(!game.getJumpsController().checkIfNextJump(player)) {
            game.checkEnd();
        }
    }

    public void scheduleJump() {
        final PlayerJumpScheduler playerJumpScheduler = new PlayerJumpScheduler(game, this);
        playerJumpScheduler.runTaskTimer(game.getInstance(), 0, 20);
    }

    public void stopSchedulerJump() {
        if(playerJumpScheduler != null) {
            playerJumpScheduler.delete();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayerJumpScheduler(PlayerJumpScheduler playerJumpScheduler) {
        this.playerJumpScheduler = playerJumpScheduler;
    }
    public PlayerJumpScheduler getPlayerJumpScheduler() {
        return playerJumpScheduler;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
    public Wool getWool() {
        return new Wool(dyeColor);
    }
    public ItemStack getStainedGlass() {
        return new ItemStack(Material.STAINED_GLASS, 1, dyeColor.getData());
    }

    public int getHealths() {
        return healths;
    }
    public boolean increaseHealth() {
        if(game.getConfigController().getAsBoolean(Config.HEALTHS_LIMITED)) {
            if(healths >= game.getConfigController().getAsInteger(Config.HEALTHS_LIMIT)) {
                return false;
            }
        }

        healths++;
        return true;
    }
    public void decreaseHealth() {
        healths--;
    }

    public int getMiddles() {
        return middles;
    }
    public void increaseMiddle() {
        middles++;
    }

    public int getSuccessJumps() {
        return successJumps;
    }
    public void increaseSuccessJump() {
        successJumps++;
    }

    public int getFailedJumps() {
        return failedJumps;
    }
    public void increaseFailedJump() {
        failedJumps++;
    }
}

package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.data.Attribute;
import fr.jielos.deacoudre.game.references.Message;
import fr.jielos.deacoudre.game.schedulers.PlayerJump;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameController extends GameComponent {

    public GameController(Game game) {
        super(game);
    }

    public void nextJump() {
        if(game.getData().getPlayers().size() > 1) {
            game.getData().incrementIndexJump();

            final Player playerJump = game.getData().getPlayers().get(game.getData().getIndexJump());
            game.getData().setPlayerJump(playerJump);

            playerJump.setGameMode(GameMode.ADVENTURE);
            playerJump.teleport(game.getConfig().getJump());

            final PlayerJump jump = new PlayerJump(game, playerJump);
            game.getData().getJumps().put(playerJump, jump);
            jump.runTaskTimer(game.getInstance(), 0, 20);

            game.getScoreboardController().updateBoards();

            game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_GO_JUMP.getAsString(), playerJump.getName()));
        } else {
            checkEnd();
        }
    }

    public void losePlayer(final Player player) {
        if(game.getData().getAttributes().containsKey(player)) {
            final Attribute attribute = game.getData().getAttributes().get(player);
            if(attribute.getHealths() <= 0) {
                eliminatePlayer(player);
            } else {
                attribute.decreaseHealth();
                game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_LOSE_HEALTH.getAsString(), player.getName(), attribute.getHealths()));

                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(game.getConfig().getJump());
            }

            removeJump(player);

            game.getData().getAttributes().replace(player, attribute);
        }
    }

    public void eliminatePlayer(final Player player) {
        game.getData().getPlayers().remove(player);

        putSpectator(player);

        game.getInstance().getServer().broadcastMessage(String.format(Message.PLAYER_ELIMINATE.getAsString(), player.getName()));
    }

    public void removeJump(final Player player) {
        if(game.getData().getJumps().containsKey(player)) {
            game.getData().getJumps().get(player).cancel();
            game.getData().getJumps().remove(player);
        }
    }

    public void putSpectator(final Player player) {
        game.getData().getPlayers().remove(player);

        if(!game.getData().getSpectators().contains(player)) {
            game.getData().getSpectators().add(player);
        }

        player.setGameMode(GameMode.SPECTATOR);
    }

    public boolean canLaunch() {
        return game.getData().getPlayers().size() >= game.getConfig().getMinPlayers();
    }

    public void checkLaunch() {
        if(canLaunch()) {
            game.launch();
        }
    }

    public void checkEnd() {
        if(game.getData().getPlayers().size() == 1) {
            game.end(game.getData().getPlayers().get(0));
        }
    }

    public boolean isPlay(final Player player) {
        return game.getData().getPlayers().contains(player) && game.getData().getAttributes().containsKey(player);
    }

    public void clearContents(final Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setLevel(0); player.setExp(0);
    }
}

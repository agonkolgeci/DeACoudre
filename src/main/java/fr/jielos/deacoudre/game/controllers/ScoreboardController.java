package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.data.Attribute;
import fr.jielos.deacoudre.game.references.Status;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardController extends GameController {

    public ScoreboardController(Game game) {
        super(game);
    }

    public void updateBoard(final Player player) {
        final BPlayerBoard playerBoard = getBoard(player);

        if(playerBoard != null) {
            playerBoard.setAll(translateBoardLines(player, game.getStatus().getScoreboardLines(), new HashMap<>()).toArray(new String[0]));
        }
    }

    public void updateBoard(final Player player, final Map<String, String> remplacements) {
        final BPlayerBoard playerBoard = getBoard(player);

        if(playerBoard != null) {
            playerBoard.setAll(translateBoardLines(player, game.getStatus().getScoreboardLines(), remplacements).toArray(new String[0]));
        }
    }

    public void updateBoards() {
        for(final Player player : game.getData().getPlayers()) {
            updateBoard(player);
        }
    }

    public BPlayerBoard getBoard(final Player player) {
        return Netherboard.instance().hasBoard(player) ? Netherboard.instance().getBoard(player): Netherboard.instance().createBoard(player, "§8- §d§lDé à coudre §8-");
    }

    public List<String> translateBoardLines(final Player player, final String[] lines, final Map<String, String> remplacements) {
        final List<String> boardLines = new ArrayList<>();
        for(String line : lines) {
            String finalLine = line;

            finalLine = finalLine.replaceAll("\\{players_count}", String.valueOf(game.getData().getPlayers().size()));

            if(game.getStatus() == Status.PLAY) {
                finalLine = finalLine.replaceAll("\\{player_jump}", game.getData().getPlayerJump().getName());
            }

            if(game.getData().getAttributes().containsKey(player)) {
                final Attribute attribute = game.getData().getAttributes().get(player);
                finalLine = finalLine
                        .replaceAll("\\{healths}", String.valueOf(attribute.getHealths()))
                        .replaceAll("\\{jumps}", String.valueOf(attribute.getJumps()));
            }

            for(final Map.Entry<String, String> remplacement : remplacements.entrySet()) {
                finalLine = finalLine.replaceAll(remplacement.getKey(), remplacement.getValue());
            }

            boardLines.add(finalLine);
        }

        return boardLines;
    }
}

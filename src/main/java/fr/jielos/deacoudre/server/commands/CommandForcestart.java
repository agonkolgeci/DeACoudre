package fr.jielos.deacoudre.server.commands;

import fr.jielos.deacoudre.Main;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.references.Status;
import fr.jielos.deacoudre.game.references.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandForcestart implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Game game = Main.getGame();
        if(game.getStatus() == Status.WAIT_FOR_PLAYERS || game.getStatus() == Status.STARTING) {
            if(game.getGameData().getPlayers().size() >= 2) {
                sender.sendMessage(Message.START_FORCED.getValue());
                game.start();

                return true;
            }
        }

        sender.sendMessage(Message.START_CANNOT_BE_FORCED.getValue());
        return true;
    }
}

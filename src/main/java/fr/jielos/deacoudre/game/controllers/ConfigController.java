package fr.jielos.deacoudre.game.controllers;

import fr.jielos.deacoudre.components.Cuboid;
import fr.jielos.deacoudre.game.Game;
import fr.jielos.deacoudre.game.GameComponent;
import fr.jielos.deacoudre.game.references.Config;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigController extends GameComponent {

    final FileConfiguration fileConfiguration;
    public ConfigController(Game game) {
        super(game);

        game.getInstance().saveDefaultConfig();
        this.fileConfiguration = game.getInstance().getConfig();
    }

    public String getAsString(final Config config) {
        return fileConfiguration.getString(config.getValue());
    }

    public int getAsInteger(final Config config) {
        int integer = fileConfiguration.getInt(config.getValue());

        switch (config) {
            case MIN_PLAYERS: {
                return Math.max(integer, 2);
            }

            case MAX_PLAYERS: {
                return Math.min(integer, game.getTeamsController().getDyeColors().size());
            }
        }

        return integer;
    }

    public boolean getAsBoolean(final Config config) {
        return fileConfiguration.getBoolean(config.getValue());
    }

    public Location getAsLocation(final Config config) {
        return (Location) fileConfiguration.get(config.getValue());
    }

    public Cuboid getAsArea(final Config config) {
        return new Cuboid((Location) fileConfiguration.get(String.format("%s.1", config.getValue())), (Location) fileConfiguration.get(String.format("%s.2", config.getValue())));
    }

}

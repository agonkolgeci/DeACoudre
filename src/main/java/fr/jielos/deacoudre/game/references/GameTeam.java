package fr.jielos.deacoudre.game.references;

public enum GameTeam {

    PLAYER("Player", "§7"),
    SPECTATOR("Spectator", "§8[§7SPEC§8] §7");

    final String name;
    final String prefix;

    GameTeam(final String name, final String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }
}

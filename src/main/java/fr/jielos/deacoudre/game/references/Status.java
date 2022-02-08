package fr.jielos.deacoudre.game.references;

public enum Status {

    WAIT(new String[]{"", "§a§lStatut", "§7- §fJoueurs: §e{players_count}", "§7- §oEn attente de j..", " ", "§eplay.jielos.fr"}),
    LAUNCH(new String[]{"", "§a§lStatut", "§7- §fJoueurs: §e{players_count}", "§7- §fLancement: §a{launch_time}s", " ", "§eplay.jielos.fr"}),
    PLAY(new String[]{"", "§a§lPartie", "§7- §fJoueurs restant: §e{players_count}", "§7- §fPlongeur: §b{player_jump}", " ", "§d§lMes statistiques", "§7- §fVie(s) supplémentaire(s): §c{healths} ❤", "§7- §fSaut(s) réussi(s): §a{jumps}", "  ", "§eplay.jielos.fr"}),
    END(new String[]{"", "§7Félicitations à §e{winner_name}", "§7qui remporte la partie !", " ", "§d§lMes statistiques", "§7- §fVie(s) supplémentaire(s): §c{healths} ❤", "§7- §fSaut(s) réussi(s): §a{jumps}", "  ", "§eplay.jielos.fr"});

    final String[] scoreboardLines;

    Status(final String[] scoreboardLines) {
        this.scoreboardLines = scoreboardLines;
    }

    public String[] getScoreboardLines() {
        return scoreboardLines;
    }
}

package fr.jielos.deacoudre.game.references;

public enum Status {

    WAIT_FOR_PLAYERS(new String[]{"", "§a§lStatut", "§7- §fJoueurs: §e{players_size}", "§7- §fCarte: §e{map_name}", " ", "§7§oEn attente de §e{players_missing} §7j§o..", "§7§opour démarrer la partie."}),
    STARTING(new String[]{"", "§a§lStatut", "§7- §fJoueurs: §e{players_size}", "§7- §fCarte: §e{map_name}", "§7- §fLancement: §a{timer_launch}s"}),
    IN_GAME(new String[]{"", "§a§lPartie", "§7- §fJoueurs restant: §e{players_size}", "§7- §fPlongeur: §b{game_jumper}", " ", "§d§lMes statistiques", "§7- §fVie(s) supp(s): §c{healths} ❤ §7{health_limit}", "§7- §fDé à coudre: §2{middles}", "§7- §fSaut(s) réussi(s): §a{success_jumps}", "§7- §fSaut(s) ratés(s): §c{failed_jumps}"}),
    END(new String[]{"", "§7Félicitations à §e{game_winner}", "§7qui remporte la partie !", " ", "§d§lMes statistiques", "§7- §fVie(s) supp(s): §c{healths} ❤ §7{health_limit}", "§7- §fDé à coudre: §2{middles}", "§7- §fSaut(s) réussi(s): §a{success_jumps}", "§7- §fSaut(s) ratés(s): §c{failed_jumps}"});

    final String[] scoreboardLines;

    Status(final String[] scoreboardLines) {
        this.scoreboardLines = scoreboardLines;
    }

    public String[] getScoreboardLines() {
        return scoreboardLines;
    }
}

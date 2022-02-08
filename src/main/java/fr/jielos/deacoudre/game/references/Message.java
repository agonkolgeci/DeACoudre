package fr.jielos.deacoudre.game.references;

public enum Message {

    PLAYER_JOIN("§7%s §ea rejoint la partie ! §a(%d/%d)"),
    PLAYER_QUIT("§7%s §ea quitté la partie ! §c(%d/%d)"),

    STARTING_SOON("§eDébut de la partie dans §6%d seconde%s §e!"),
    STARTING_CANCELED("§c§oLancement de la partie annulé, il n'y a pas assez de joueurs!"),
    STARTING("§eLancement de la partie en cours§6.. §e!"),

    START_FORCED("§7§oVous avez forcé le démarrage de la partie."),
    START_CANNOT_BE_FORCED("§c§oVous ne pouvez pas forcer le démarrage de la partie, puisque celle-ci a déjà démarrer ou alors il n'y a pas assez de joueurs."),

    PLAYER_LEAVE("§7§o%s a quitté la partie!"),
    PLAYER_ELIMINATE("§7§l%s§r §cest éliminé, ravi d'avoir fait ta connaissance !"),
    PLAYER_SUCCESSFUL_JUMP("§7§l%s§r §ea réussi son saut, un vrai plongeur !"),
    PLAYER_WIN_HEALTH("§7§l%s§r §evient de faire un incroyable §6Dé à coudre §e! Il remporte une vie supplémentaire §8(§7+1 §c§l❤§8)"),
    PLAYER_LOSE_HEALTH("§7§l%s§r §cvient de perdre une vie, %d vie(s) supplémentaire(s) restante(s)!"),
    PLAYER_GO_JUMP("§6C'est au tour de §7§l%s§r §6de sauter !"),

    PLAYER_WIN_GAME("§6Félicitations à §a§l%s§r §6qui remporte la partie avec un total de §7§l%d vie(s) supplémentaire(s) §6& §7§l%d saut(s) réussi(s) §6!"),
    KICK_GAME_END("\n§4Impossible de rejoindre cette partie!\n§cLa partie est malheureusement terminée.");

    final String string;

    Message(final String string) {
        this.string = string;
    }

    public String getAsString() {
        return string;
    }
}

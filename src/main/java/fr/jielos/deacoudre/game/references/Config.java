package fr.jielos.deacoudre.game.references;

public enum Config {

    MIN_PLAYERS("min-players"),
    MAX_PLAYERS("max-players"),

    MAP_NAME("map-name"),

    HEALTHS_LIMITED("healths-limited"),
    HEALTHS_LIMIT("healths-limit"),

    TIMER_LAUNCH("timer-launch"),
    TIMER_JUMP("timer-jump"),
    TIMER_END("timer-end"),

    SCOREBOARD_TITLE("scoreboard.title"),
    SCOREBOARD_FOOTER_ENABLED("scoreboard.footer.enabled"),
    SCOREBOARD_FOOTER_CONTENT("scoreboard.footer.content"),

    WAITING_ROOM("locations.waiting-room"),
    JUMP("locations.jump"),
    END("locations.end"),
    POOL("locations.pool");

    final String value;

    Config(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

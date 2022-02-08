package fr.jielos.deacoudre.game.data;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

public class Attribute {

    final Player player;
    final DyeColor dyeColor;

    int jumps;
    int healths;

    public Attribute(final Player player, final DyeColor dyeColor) {
        this.player = player;
        this.dyeColor = dyeColor;

        this.jumps = 0;
        this.healths = 0;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
    public Wool getWool() {
        return new Wool(dyeColor);
    }

    public int getHealths() {
        return healths;
    }
    public void increaseHealth() {
        healths++;
    }
    public void decreaseHealth() {
        healths--;
    }

    public int getJumps() {
        return jumps;
    }
    public void increaseJump() {
        jumps++;
    }
    public void decreaseJump() {
        jumps--;
    }
}

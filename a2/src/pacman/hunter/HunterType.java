package pacman.hunter;

import java.io.Serializable;

/**
 * Definition of HunterType's available in the game.
 *
 * @ass1
 */
public enum HunterType implements Serializable, Comparable<HunterType> {
    /**
     * Can run temporarily.
     */
    SPEEDY,
    /**
     * Can phase through ghosts temporarily.
     */
    PHASEY,
    /**
     * Can eat ghosts temporarily.
     */
    HUNGRY,
    /**
     * basic phil, has no special ability.
     */
    PHIL
}

package pacman.hunter;

import pacman.game.Entity;
import pacman.ghost.Ghost;
import pacman.ghost.Phase;
import pacman.util.Direction;
import pacman.util.Position;

/*Hunters are entities which are controlled by the player to clear the board and
 win the game. Hunters in this version have special abilities like "phasing
 through ghost's" and "run's two blocks at a time" and so on. These special
 abilities are too add a bit of variety into the game.
*/
public abstract class Hunter extends Entity {
    private boolean alive;
    private int specialDuration;
    private boolean specialActive;
    private boolean specialUsed;

    /**
     * Creates a Hunter setting the hunter to be alive with the following
     * conditions: The hunter has not used it's special yet. The hunter also
     * does not have its special active. This hunter has a position of (0, 0)
     * with a direction of UP.
     */

    public Hunter() {
        super(new Position(0,0), Direction.UP);
        this.alive = true;
        this.specialActive = false;
        this.specialUsed = false;
        this.specialDuration = 0;
    }

    /**
     * Creates a Hunter with attributes that are equal than the original
     * @param original hunter to copy.
     */

    public Hunter(Hunter original) {
        super(original.getPosition(), original.getDirection());
        this.alive = !(original.isDead());
        this.specialUsed = original.specialUsed;
        this.specialActive = original.specialActive;
        this.specialDuration = original.specialDuration;
    }

    /**
     * Activates the hunter's special if the hunter hasn't already used its
     * special before. If the hunter has already used its special then do not
     * change the special duration. If the duration for the special is greater
     * than zero then use the hunter's special and set the special's duration to
     * the given duration. If the duration for the special is zero or lower than
     * do not change the special duration and do not use up the hunter's special.
     * @param duration to activate the special for.
     */

    public void activateSpecial(int duration) {
        if(specialUsed = false && duration > 0) {
            this.specialUsed = true;
            this.specialActive = true;
            this.specialDuration = duration;//??
        }
    }

    /**
     * Gets how many ticks of our special ability is remaining.
     * @return the amount of ticks remaining for the special.
     */

    public int getSpecialDurationRemaining() {
        return this.specialDuration;
        //??
    }

    /**
     * Tells if the hunter is dead.
     * @return true if dead, false otherwise.
     */

    public boolean isDead(){
        return !this.alive;
    }

    /**
     * Checks if the special is currently active.
     * @return true if the special ability has a duration remaining that is
     * greater than 0 ticks.
     */

    public boolean isSpecialActive(){
        return this.getSpecialDurationRemaining() > 0;
    }

    /**
     * Checks to see if the hunter is at the same position of the ghost. If the
     * ghost and hunter do have the same position then if the ghost is
     * Phase.FRIGHTENED the ghost is killed Ghost.kill() otherwise the ghost
     * kills the hunter. If the ghost and hunter are not at the same position
     * then do nothing.
     * @param ghost to check if were colliding with.
     * @throws NullPointerException if ghost is null.
     */

    public void hit(Ghost ghost) throws NullPointerException {
        if(this.getPosition().equals(ghost.getPosition())) {
            if (ghost.getPhase().equals(Phase.FRIGHTENED)) {
                ghost.kill();
            } else {
                this.alive = false;
            }
        }
    }

    /**
     * Resets this hunter to be:
     * Alive
     * With a special that has not been used yet
     * A special that is not active ( duration of 0 )
     * With a Direction of Direction.UP
     * With a Position of ( 0, 0 )
     */

    public void reset() {
        this.setDirection(Direction.UP);
        this.setPosition(new Position(0,0));
        this.alive = true;
        this.specialUsed = false;
        this.specialActive = false;
        this.specialDuration = 0;
    }
}

package pacman.ghost;

import pacman.game.Entity;
import pacman.util.Direction;
import pacman.util.Position;

//An Abstract Ghost which is a game entity.
public abstract class Ghost extends Entity {
    private boolean alive;
    private Direction ghostDir;
    private Position ghostPos;
    private Phase ghostPhase;
    private int phaseDuration;

    /**
     * Creates a ghost which is alive and starts in the SCATTER phase with a
     * duration of Phase.SCATTER.duration(). This ghost also has a default
     * position of (0, 0) and a default direction of facing up.
     */

    public Ghost() {
        this.alive = true;
        this.ghostPhase = Phase.SCATTER;
        this.phaseDuration = ghostPhase.getDuration();
        this.ghostDir = Direction.UP;
        this.ghostPos = new Position(0,0);
    }

    /**
     * Gets the ghosts colour.
     * @return hex version of the ghosts colour, e.g. #FFFFFF for white.
     */

    public abstract String getColour();

    /**
     * Gets the ghosts type.
     * @return this ghost type.
     */

    public abstract GhostType getType();

    /**
     * Sets the Ghost Phase and its duration overriding any current phase
     * information. if Phase is null then no changes are made. If the duration
     * is less than zero then the duration is set to 0.
     * @param newPhase to set the ghost to.
     * @param duration of ticks for the phase to last for.
     */

    public void setPhase(Phase newPhase, int duration) {
        if (newPhase != null) {
            this.ghostPhase = newPhase;
            if (duration <= 0) {
                this.phaseDuration = 0;
            } else {
                this.phaseDuration = duration;
            }
        }
    }

    /**
     * Get the phase that the ghost currently is in.
     * @return the set phase.
     */

    public Phase getPhase(){
        return this.ghostPhase;
    }

    /**
     * Gets the phase info of the ghost.
     * @return the phase and duration formatted as such: "PHASE:DURATION".
     */

    public String phaseInfo(){
        return " " + this.getPhase() + " : " + this.phaseDuration;
    }

    /**
     * Checks if this ghost is dead.
     * @return true if dead, false otherwise.
     */

    public boolean isDead(){
        return !this.alive;
    }

    /**
     * Kills this ghost by setting its status to isDead.
     */

    public void kill(){
        this.alive = false;
    }

    /**
     * Resets the ghost back to an initial state where:
     * It is alive
     * With a Phase of SCATTER with duration SCATTER.getDuration()
     * Facing in the Direction.UP
     * With a Position of ( 0, 0 )
     */

    public void reset() {
        this.alive = true;
        this.ghostPhase = Phase.SCATTER;
        this.phaseDuration = ghostPhase.getDuration();
        this.ghostDir = Direction.UP;
        this.ghostPos = new Position(0,0);
    }
}

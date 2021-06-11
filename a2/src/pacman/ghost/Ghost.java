package pacman.ghost;

import pacman.board.PacmanBoard;
import pacman.game.Entity;
import pacman.game.PacmanGame;
import pacman.util.Direction;
import pacman.util.Position;

import java.util.*;

/**
 * An Abstract Ghost which is a game entity.
 *
 * @ass1
 */
public abstract class Ghost extends Entity {

    // whether the ghost is dead
    private boolean dead;
    // current phase of this ghost
    private Phase phase;
    // duration of current phase
    private int phaseDuration;

    /**
     * Creates a ghost which is alive and starts in the SCATTER phase
     * with a duration of Phase.SCATTER.duration(). This ghost also has
     * a default position of (0, 0) and a default direction of facing
     * up.
     *
     * @ass1
     */
    public Ghost() {
        super();
        dead = false;
        phase = Phase.SCATTER;
        phaseDuration = Phase.SCATTER.getDuration();
    }

    /**
     * Sets the Ghost Phase and its duration overriding any current
     * phase information.
     *
     * if Phase is null then no changes are made. If the duration is
     * less than zero then the duration is set to 0.
     *
     * @param newPhase to set the ghost to.
     * @param duration of ticks for the phase to last for.
     * @ass1
     */
    public void setPhase(Phase newPhase, int duration) {
        if (newPhase != null) {
            phase = newPhase;
            phaseDuration = Integer.max(0, duration);
        }
    }

    /**
     * Get the phase that the ghost currently is in.
     * @return the set phase.
     * @ass1
     */
    public Phase getPhase() {
        return phase;
    }

    /*
     * NextPhase decreases our phase duration and moves us to the
     * next phase if it is 0.
     *
     * - CHASE goes to SCATTER.
     * - FRIGHTENED && SCATTER go to CHASE.
     */
    private void nextPhase() {
        phaseDuration = Integer.max(0, phaseDuration - 1);
        if (phaseDuration == 0) {
            switch (getPhase()) {
                case CHASE:
                    setPhase(Phase.SCATTER, Phase.SCATTER.getDuration());
                    break;
                case FRIGHTENED:
                case SCATTER:
                    setPhase(Phase.CHASE, Phase.CHASE.getDuration());
                    break;
            }
        }
    }

    /**
     * Gets the phase info of the ghost.
     * @return the phase and duration formatted as such: "PHASE:DURATION".
     * @ass1
     */
    public String phaseInfo() {
        return String.format("%s:%d", phase, phaseDuration);
    }

    /**
     * Gets the ghosts colour.
     * @return hex version of the ghosts colour, e.g. #FFFFFF for white.
     * @ass1
     */
    public abstract String getColour();

    /**
     * Gets the ghosts type.
     * @return this ghosts type.
     * @ass1
     */
    public abstract GhostType getType();

    /**
     * Kills this ghost by setting its status to isDead.
     * @ass1
     */
    public void kill() {
        this.dead = true;
    }

    /**
     * Checks if this ghost is dead.
     * @return true if dead, false otherwise.
     * @ass1
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Resets the ghost back to an initial state where:
     *
     * <ul>
     *     <li>It is alive</li>
     *     <li>With a Phase of SCATTER with duration SCATTER.getDuration()</li>
     *     <li>Facing in the Direction.UP</li>
     *     <li>With a Position of ( 0, 0 )</li>
     * </ul>
     * @ass1
     */
    public void reset() {
        dead = false;
        this.phase = Phase.SCATTER;
        this.phaseDuration = Phase.SCATTER.getDuration();
        this.setDirection(Direction.UP);
        this.setPosition(new Position(0, 0));
    }

    /**
     * Gets the target block that we should be heading towards when in the
     * chase phase.
     * @param game to read the board from.
     * @return the ghosts target position.
     */
    public abstract Position chaseTarget(PacmanGame game);

    /**
     * Gets the home block that we should be heading towards when in the
     * scatter phase.
     * @param game to read the board from.
     * @return the ghosts home position.
     */
    public abstract Position home(PacmanGame game);

    /**
     * Move advances the ghost in a direction by one point on the board.
     * The direction this move is made is done as follows:
     * Decrease the phase duration by 1, and if the duration is now zero,
     * then move to the next phase.
     * Get the target position. If the phase is CHASE, then get the chaseTarget.
     * If the phase is SCATTER, then the position is the ghost's home position.
     * If the phase is FRIGHTENED, choose a target position with coordinates:
     * targetPositionX = (x*24 mod (2 * board width )) - board width,
     * targetPositionY = (y*36 mod (2 * board height)) - board height
     * where x and y are the current coordinates of the ghost.
     * Choose the direction that the current Ghost position when moved 1 step
     * has the smallest euclidean distance to the target position. The board
     * item in the move position must be pathable for it to be chosen.
     * The chosen direction cannot be opposite to the current direction.
     * If multiple directions have the same shortest distance, then choose the
     * direction in the order UP, LEFT, DOWN, RIGHT
     * Set the direction of the Ghost to the chosen direction.
     * Set the position of this Ghost to be one forward step in the chosen
     * direction.
     * Note: The next phase after CHASE is SCATTER. Note: The next phase after
     * FRIGHTENED or SCATTER is CHASE.
     * @param game information needed to decide movement.
     */
    public void move(PacmanGame game) {
        this.nextPhase();
        Position newPosition = null;
        switch (phase) {
            case FRIGHTENED:
                int targetPositionX = ((this.getPosition().getX() * 24) % (2 *
                        game.getBoard().getWidth())) - game.getBoard().getWidth();
                int targetPositionY = ((this.getPosition().getY() * 36) % (2 *
                        game.getBoard().getHeight())) - game.getBoard().getHeight();
                newPosition = new Position(targetPositionX, targetPositionY);
                break;
            case SCATTER:
                newPosition = this.home(game);
                break;
            case CHASE:
                newPosition = this.chaseTarget(game);
        }
        this.setDirection(calculateDistance(newPosition, game.getBoard()));
        switch (this.getDirection()) {
            case UP:
                this.setPosition(this.getPosition().add(new Position(1, 0)));
                break;
            case LEFT:
                this.setPosition(this.getPosition().add(new Position(0, -1)));
                break;
            case RIGHT:
                this.setPosition(this.getPosition().add(new Position(0, 1)));
                break;
            case DOWN:
                this.setPosition(this.getPosition().add(new Position(-1, 0)));
        }
    }

    /**
     * Helper method that calculates euclidean distance from all possible
     * new position of a ghost to its target destination. It uses an array to
     * first sort the distances and then uses pile to select the candidate
     * directions.
     */
    private Direction calculateDistance(Position end, PacmanBoard board) {
        ArrayList<Direction> containsDir = new ArrayList<>();
        ArrayList<Direction> candidateDir = new ArrayList<>();
        ArrayList<Double> listDistances = new ArrayList<>();
        Stack<Double> pile = new Stack<>();
        Position newPositionUp = this.getPosition().add(new Position(1, 0));
        Position newPositionDown = this.getPosition().add(new Position(-1, 0));
        Position newPositionRight = this.getPosition().add(new Position(0, 1));
        Position newPositionLeft = this.getPosition().add(new Position(0, -1));
        listDistances.add(newPositionUp.distance(end));
        listDistances.add(newPositionDown.distance(end));
        listDistances.add(newPositionRight.distance(end));
        listDistances.add(newPositionLeft.distance(end));
        Collections.sort(listDistances, Collections.reverseOrder());

        for (int i = listDistances.size(); i > 0; i--) {
            pile.push(listDistances.get(i-1));
        }
        while (!pile.isEmpty()){
            Double topVal = pile.pop();
            if (topVal == newPositionUp.distance(end) &&
                    !containsDir.contains(Direction.UP)) {
                containsDir.add(Direction.UP);
                if (board.getEntry(newPositionUp).getPathable() &&
                        Direction.UP != this.getDirection().opposite()){
                    candidateDir.add(Direction.UP);
                }
            } else if (topVal == newPositionDown.distance(end) &&
                    !containsDir.contains(Direction.DOWN)) {
                containsDir.add(Direction.DOWN);
                if (board.getEntry(newPositionDown).getPathable() &&
                        Direction.DOWN != this.getDirection().opposite()){
                    candidateDir.add(Direction.DOWN);
                }
            } else if (topVal == newPositionRight.distance(end) &&
                    !containsDir.contains(Direction.RIGHT)) {
                containsDir.add(Direction.RIGHT);
                if (board.getEntry(newPositionRight).getPathable() &&
                        Direction.RIGHT != this.getDirection().opposite()){
                    candidateDir.add(Direction.RIGHT);
                }
            } else {
                containsDir.add(Direction.LEFT);
                if (board.getEntry(newPositionLeft).getPathable() &&
                        Direction.LEFT != this.getDirection().opposite()){
                    candidateDir.add(Direction.LEFT);
                }
            }
        }
        return selectPosition(candidateDir);
    }

    /**
     * Selects a direction from the candidate directions based on the priority
     * list {UP, LEFT, DOWN, RIGHT}
     * @param candidates possible direction to be selected
     * @return choosen direction
     */
    private Direction selectPosition(ArrayList<Direction> candidates) {
        ArrayList<Direction> directionPriority = new ArrayList<>();
        directionPriority.add(Direction.UP);
        directionPriority.add(Direction.LEFT);
        directionPriority.add(Direction.DOWN);
        directionPriority.add(Direction.RIGHT);
        for (int i = 0; i < directionPriority.size(); i++) {
            for (int j = 0; j < candidates.size(); j++) {
                if (candidates.get(j).equals(directionPriority.get(i))) {
                    return candidates.get(j);
                }
            }
        }
        return null;
    }

    /**
     * Checks if another object instance is equal to this Ghost. Ghosts are
     * equal if they have the same alive/dead status, phase duration ,current
     * phase, direction and position.
     * @param o object to compare.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        try {
            Ghost newGhost = (Ghost) o;
            if (newGhost.isDead()==this.isDead()) {
                if (newGhost.phaseInfo().equals(this.phaseInfo())) {
                    if (newGhost.getDirection()==this.getDirection() &&
                            newGhost.getPosition().equals(this.getPosition())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * For two objects that are equal the hash should also be equal.
     * For two objects that are not equal the hash does not have to be different.
     * @return hash of PacmanBoard.
     */
    @Override
    public int hashCode() {
        switch (this.getDirection()) {
            case DOWN:
                return this.getPosition().hashCode() * -1;
            case RIGHT:
                return this.getPosition().hashCode() * 2;
            case LEFT:
                return this.getPosition().hashCode() / 2;
            case UP:
                return this.getPosition().hashCode() * 21;
        }
        return 0;
    }

    /**
     * Represents this Ghost in a comma-seperated string format. Format is:
     * "x,y,DIRECTION,PHASE:phaseDuration". DIRECTION is the uppercase enum
     * type value for Direction. PHASE is the uppercase enum type value for
     * Phase. Example: "4,5,LEFT,FRIGHTENED:15"
     * @return "x,y,DIRECTION,PHASE:phaseDuration"
     */
    public String toString() {
        return this.getPosition().getX() + "," + this.getPosition().getY() +
                "," + this.getDirection() + "," + this.phaseInfo();
    }

}

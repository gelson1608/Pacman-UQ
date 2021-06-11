package pacman.ghost;

import pacman.game.PacmanGame;
import pacman.util.Position;

/**
 * Pinky is a cunning ghost that tries to ambush the hunter.
 * When not chasing the hunter down, Pinky likes to hang out in
 * the top left corner of the board in a pink glow.
 *
 * @ass1
 */
public class Pinky extends Ghost {

    /**
     * Get Pinky colour.
     * @return "#c397d8"
     * @ass1
     */
    @Override
    public String getColour() {
        return "#c397d8";
    }

    /**
     * Get Pinkys type/name.
     * @return PINKY;
     * @ass1
     */
    @Override
    public GhostType getType() {
        return GhostType.PINKY;
    }

    /**
     * Pinky will chase 4 blocks in front of the hunter's current direction.
     * @param game to read the board from.
     * @return the position 4 blocks in front of hunter position.
     */
    @Override
    public Position chaseTarget(PacmanGame game) {
        Position chasingPosition = game.getHunter().getPosition();
        switch (game.getHunter().getDirection()) {
            case UP:
                chasingPosition.add(new Position(4, 0));
                break;
            case LEFT:
                chasingPosition.add(new Position(0, -4));
                break;
            case RIGHT:
                chasingPosition.add(new Position(0, 4));
                break;
            case DOWN:
                chasingPosition.add(new Position(-4, 0));
        }
        return chasingPosition;
    }

    /**
     * Pinky's home position is one block outside of the top left of the game
     * board. Where the top left position of the board is (0, 0).
     * @param game to read the board from.
     * @return One diagional block out from the top left corner.
     */
    @Override
    public Position home(PacmanGame game) {
        return new Position(1, -1);
    }
}

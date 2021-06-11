package pacman.ghost;

import pacman.game.PacmanGame;
import pacman.util.Position;

/**
 * Inky is a ghost that likes to tail close behind the hunter.
 * When not chasing the hunter down, Inky likes to hang out in
 * the bottom right corner of the board in a blue glow.
 *
 * @ass1
 */
public class Inky extends Ghost {

    /**
     * Get Inky's colour.
     * @return "#7aa6da"
     * @ass1
     */
    @Override
    public String getColour() {
        return "#7aa6da";
    }

    /**
     * Get Inky's type/name.
     * @return INKY;
     * @ass1
     */
    @Override
    public GhostType getType() {
        return GhostType.INKY;
    }

    /**
     * Inky will chase 2 blocks behind the hunter's current direction.
     * @param game to read the board from.
     * @return the position 2 blocks behind hunter position.
     */
    @Override
    public Position chaseTarget(PacmanGame game) {
        Position chasingPosition = game.getHunter().getPosition();
        switch (game.getHunter().getDirection()) {
            case UP:
                chasingPosition.add(new Position(-2, 0));
                break;
            case LEFT:
                chasingPosition.add(new Position(0, 2));
                break;
            case RIGHT:
                chasingPosition.add(new Position(0, -2));
                break;
            case DOWN:
                chasingPosition.add(new Position(2, 0));
        }
        return chasingPosition;
    }

    /**
     * Inky's home position is one block outside of the bottom right of the game
     * board. Where the top left position of the board is (0, 0).
     * @param game to read the board from.
     * @return One diagonal block out from the bottom right corner.
     */
    @Override
    public Position home(PacmanGame game) {
        int height = game.getBoard().getHeight();
        int width = game.getBoard().getWidth();
        return new Position(- (height + 1), width + 1);
    }
}

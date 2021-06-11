package pacman.util;

//Direction represents directions in a 2D plane. Each direction stores a relative position
public enum Direction {
    LEFT,   //Facing to the left.
    RIGHT,  //Facing to the right.
    UP,     //Facing up.
    DOWN;   //Facing down.

    private Position offsetPosition;
    private Direction oppositeDir;

    /**
     * Gets the offset associated with this direction.
     * @return relative position offset.
     */

    public Position offset() {
        switch (this) {
            case LEFT:
                offsetPosition = new Position(-1,0);
                break;
            case RIGHT:
                offsetPosition = new Position(1,0);
                break;
            case UP:
                offsetPosition = new Position(0,1);
                break;
            case DOWN:
                offsetPosition = new Position(0,-1);
        }
        return  offsetPosition;
    }

    /**
     * Gets the opposite direction to this direction.
     * @return
     */

    public Direction opposite() {
        switch (this) {
            case LEFT:
                oppositeDir = RIGHT;
                break;
            case RIGHT:
                oppositeDir = LEFT;
                break;
            case DOWN:
                oppositeDir = UP;
                break;
            case UP:
                oppositeDir = DOWN;
        }
        return oppositeDir;
    }
}

package pacman.game;

import pacman.util.Direction;
import pacman.util.Position;

//Entity A entity is the animated objects in the game that can traverse the game
//board and interact with other entities.
public abstract class Entity implements Moveable {
    private Direction direction;
    private Position position;

    /**
     * Creates an entity that is at position (0, 0) and is facing UP.
     */

    public Entity() {
        this.position = new Position(0,0);
        this.direction = Direction.UP;
    }

    /**
     * Creates an entity that is at the given position facing in the given
     * direction. If the position is null then the position will be the same as
     * the default position ( 0, 0 ). If the direction is null then the
     * direction will be the same as the default ( UP ).
     * @param position to be set to.
     * @param direction to be facing.
     */

    public Entity(Position position, Direction direction) {
        if (position==null) {
            this.position = new Position(0,0);
        } else {
            this.position = position;
        }

        if (direction==null) {
            this.direction = Direction.UP;
        } else {
            this.direction = direction;
        }
    }

    /**
     * Gets the direction that this Moveable is facing.
     * @return the current direction of the Movable.
     */

    public Direction getDirection(){
        return this.direction;
    }

    /**
     * Gets the current position of the Moveable
     * @return current position.
     */

    public Position getPosition(){
        return this.position;
    }

    /**
     * Sets the direction of the entity, if direction is null the direction is
     * not set and remains the same.
     * @param direction to be set.
     */

    public void setDirection(Direction direction) {
        if (direction != null) {
            this.direction = direction;
        }
    }

    /**
     * Sets the position of the entity, if position is null the position is not
     * set.
     * @param position toset to the Moveable.
     */

    public void setPosition(Position position) {
        if (position != null) {
            this.position = position;
        }
    }
}

package pacman.util;
import java.awt.*;
import java.lang.Math;

//Similar to a Point Class but instead called Position.
public class Position {
    private int coordinateX;
    private int coordinateY;

    /**
     * Creates a position at the given x and y coordinates.
     * @param x location
     * @param y location
     */

    public Position(int x, int y) {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    /**
     * Adds two positions together.
     * @param other position to add to this one.
     * @return This position + other.
     */

    public Position add(Position other) {
        return new Position(coordinateX + other.getX(),
                coordinateY + other.getY());
    }

    /**
     * Calculates the Euclidean distance from this point to the given other point.
     * @param other point used to calculate the euclidean distance.
     * @return the euclidean distance.
     */
    public double distance(Position other) {
        int xDifference = Math.abs(other.coordinateX - this.coordinateX);
        int yDifference = Math.abs(other.coordinateY - this.coordinateY);
        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }

    /**
     * Checks if two positions are equal.
     * @param other object to compare against.
     * @return true if x == this.x and also y == this.y, false otherwise.
     */

    @Override
    public boolean equals(Object other) {
        if ( !(other instanceof Position)) {
            return false;
        }
        Position another = (Position) other;
        return another.getX()==this.getX() && another.getY()==this.getY();
    }

    /**
     * Gets the X axis location.
     * @return x position.
     */

    public int getX(){
        return this.coordinateX;
    }

    /**
     * Gets the Y axis location.
     * @return y position.
     */

    public int getY(){
        return this.coordinateY;
    }

    /**
     * Calculates the hash of the position.
     * For two objects that are equal the hash should also be equal. For two
     * objects that are not equal the hash does not have to be different.
     * @return hash of this position.
     */

    @Override
    public int hashCode(){
        return 7 * this.getX() + ((this.getY() + this.getX()) *
                (this.getY() + this.getX() + 1));
    }

    /**
     * Multiplies by a factor on the x and y axis.
     * @param factor to multiple the axis by.
     * @return a new position with the x axis scaled by factor and y axis scaled
     * by factor.
     */

    public Position multiply(int factor) {
        int multipliedX = this.coordinateX * factor;
        int multipliedY = this.coordinateY * factor;
        return new Position(multipliedX, multipliedY);
    }

}

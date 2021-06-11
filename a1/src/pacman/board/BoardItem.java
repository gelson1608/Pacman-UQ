package pacman.board;

import java.util.Map;

//This enum defines different items that are placed on the board. Items may have
//a pickup score if the item can be picked up. Items must define whether they are path-able.
public enum BoardItem {
    NONE, //A nothing item ( Empty )
    WALL, //A wall tile
    DOT,    //A dot
    BIG_DOT, //A big dot
    BIG_DOT_SPAWN,  //A big dot spawn point
    GHOST_SPAWN,    //A spawn point for GHOSTS
    PACMAN_SPAWN;   //A spawn point for PACMAN

    private char itemChar;
    private int itemScore;
    private boolean pathable;

    /**
     * gets the character key of the item.
     * @return the character key associated with the item.
     */

    public char getChar() {
        switch (this) {
            case NONE:
                itemChar = '0';
                break;
            case WALL:
                itemChar = 'X';
                break;
            case DOT:
                itemChar = '1';
                break;
            case BIG_DOT:
                itemChar = 'B';
                break;
            case BIG_DOT_SPAWN:
                itemChar = 'b';
                break;
            case GHOST_SPAWN:
                itemChar = '$';
                break;
            case PACMAN_SPAWN:
                itemChar = 'P';
        }
        return itemChar;
    }

    /**
     * Takes a character and returns the associated BoardItem as presented in
     * the Enum comment's "Enum definition" table
     * @param key a character that represents the board item. The acceptable
     *            characters are defined in the Enum's "Enum definition" table.
     * @return the board Item associated with the character
     * @throws IllegalArgumentException if the character is not part of the
     * supported Items
     */

    public static BoardItem getItem(char key) throws IllegalArgumentException {
        BoardItem boardItem = null;
        switch (key) {
            case '0':
                boardItem = NONE;
                break;
            case 'X':
                boardItem = WALL;
                break;
            case '1':
                boardItem = DOT;
                break;
            case 'B':
                boardItem = BIG_DOT;
                break;
            case 'b':
                boardItem = BIG_DOT_SPAWN;
                break;
            case '$':
                boardItem = GHOST_SPAWN;
                break;
            case 'P':
                boardItem = PACMAN_SPAWN;
        }
        return boardItem;
    }

    /**
     * gets the path-able nature of the item.
     * @return whether the item is path-able.
     */

    public boolean getPathable() {
        switch (this) {
            case NONE:
                pathable = true;
                break;
            case WALL:
                pathable = false;
                break;
            case DOT:
                pathable = true;
                break;
            case BIG_DOT:
                pathable = true;
                break;
            case BIG_DOT_SPAWN:
                pathable = true;
                break;
            case GHOST_SPAWN:
                pathable = true;
                break;
            case PACMAN_SPAWN:
                pathable = true;
        }
        return pathable;
    }

    /**
     * gets the score of the item.
     * @return the score associated with an item.
     */

    public int getScore() {
        switch (this) {
            case NONE:
                itemScore = 0;
                break;
            case WALL:
                itemScore = 0;
                break;
            case DOT:
                itemScore = 10;
                break;
            case BIG_DOT:
                itemScore = 15;
                break;
            case BIG_DOT_SPAWN:
                itemScore = 0;
                break;
            case GHOST_SPAWN:
                itemScore = 0;
                 break;
            case PACMAN_SPAWN:
                itemScore = 0;
        }
        return itemScore;
    }
}

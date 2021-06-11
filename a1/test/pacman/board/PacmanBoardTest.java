package pacman.board;

import org.junit.Test;
import pacman.util.Position;

import static org.junit.Assert.*;

public class PacmanBoardTest {

    @Test
    public void testPacmanBoardException() {
        try {
            PacmanBoard pacmanBoard = new PacmanBoard(1,0);
            fail();
        } catch (IllegalArgumentException e) {
            //nothing
        }
    }

    @Test
    public void testGetWidth() {
        PacmanBoard pacmanBoard = new PacmanBoard(10,5);
        assertEquals(10, pacmanBoard.getWidth());
    }

    @Test
    public void testGetHeight() {
        PacmanBoard pacmanBoard = new PacmanBoard(20,5);
        assertEquals(5, pacmanBoard.getHeight());
    }

    @Test
    public void testGetEntry() {
        PacmanBoard pacmanBoard = new PacmanBoard(20,5);
        Position outOfBoundsPosition = new Position(20, 6);
        Position position = new Position(10, 3);
        try {
            pacmanBoard.getEntry(null);
            fail();
        } catch (NullPointerException e) {
            try {
                pacmanBoard.getEntry(outOfBoundsPosition);
                fail();
            } catch (IndexOutOfBoundsException e1) {
                //nothing
            }
        } finally {
            assert(pacmanBoard.getEntry(position) instanceof BoardItem);
        }
    }

    @Test
    public void testPacmanBoardItems() {
        PacmanBoard pacmanBoard = new PacmanBoard(40,20);
        for (int i = 1; i < pacmanBoard.getWidth() - 1; i++){
            for (int j = 1; j < pacmanBoard.getHeight() - 1; j++) {
                Position position = new Position(i, j);
                assertEquals(pacmanBoard.getEntry(position), BoardItem.NONE);
            }
        }
        for (int i = 0; i < pacmanBoard.getHeight(); i++ ) {
            Position position1 = new Position(0, i);
            Position position2 = new Position(pacmanBoard.getWidth() - 1, i);
            assertEquals(pacmanBoard.getEntry(position1), BoardItem.WALL);
            assertEquals(pacmanBoard.getEntry(position2), BoardItem.WALL);
        }
        for (int i = 0; i < pacmanBoard.getWidth(); i++ ) {
            Position position1 = new Position(i, 0);
            Position position2 = new Position(i, pacmanBoard.getHeight() - 1);
            assertEquals(pacmanBoard.getEntry(position1), BoardItem.WALL);
            assertEquals(pacmanBoard.getEntry(position2), BoardItem.WALL);
        }
    }

    @Test
    public void testPacmanBoardDeepCopy() {
        PacmanBoard pacmanBoard = new PacmanBoard(4,4);
        try {
            PacmanBoard newPacmanBoard = new PacmanBoard(null);
            fail();
        } catch (NullPointerException e) {
            //nothing
        } finally {
            PacmanBoard newPacmanBoard = new PacmanBoard(pacmanBoard);
            assertFalse(newPacmanBoard==pacmanBoard);
            for (int i = 0; i < newPacmanBoard.getWidth(); i++) {
                for (int j = 0; j < newPacmanBoard.getHeight(); j++) {
                    Position position = new Position(i, j);
                    assertEquals(newPacmanBoard.getEntry(position),
                            pacmanBoard.getEntry(position));
                }
            }
        }
    }

    @Test
    public void testSetEntry() {
        PacmanBoard pacmanBoard = new PacmanBoard(5,5);
        Position position1 = new Position(3, 3);
        Position position2 = new Position(3, 2);
        Position position3 = new Position(3, 1);
        Position position4 = new Position(2, 2);
        Position position5 = new Position(2, 1);
        pacmanBoard.setEntry(position1, BoardItem.DOT);
        assertEquals(BoardItem.DOT, pacmanBoard.getEntry(position1));

        assertEquals(BoardItem.NONE, pacmanBoard.getEntry(position2));
        pacmanBoard.setEntry(position2, BoardItem.PACMAN_SPAWN);
        assertEquals(BoardItem.PACMAN_SPAWN, pacmanBoard.getEntry(position2));

        pacmanBoard.setEntry(position3, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.GHOST_SPAWN, pacmanBoard.getEntry(position3));

        pacmanBoard.setEntry(position4, BoardItem.PACMAN_SPAWN);
        assertEquals(BoardItem.NONE, pacmanBoard.getEntry(position2));
        assertEquals(BoardItem.PACMAN_SPAWN, pacmanBoard.getEntry(position4));

        pacmanBoard.setEntry(position5, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.NONE, pacmanBoard.getEntry(position3));
        assertEquals(BoardItem.GHOST_SPAWN, pacmanBoard.getEntry(position5));


        Position outOfBoundsPosition = new Position(20, 6);
        try {
            pacmanBoard.setEntry(null, BoardItem.DOT);
            fail();
        } catch (NullPointerException e) {

        }
        try {
            pacmanBoard.setEntry(outOfBoundsPosition, BoardItem.DOT);
            fail();
        } catch (IndexOutOfBoundsException e1) {

        }

        try {
            pacmanBoard.setEntry(new Position(1, 3), null);
            fail();
        } catch (NullPointerException e2) {

        }
    }

    @Test
    public void testGetGhostSpawn() {
        PacmanBoard pacmanBoard = new PacmanBoard(5,5);
        Position position = new Position(3, 3);
        pacmanBoard.setEntry(position, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.GHOST_SPAWN, pacmanBoard.getEntry(position));
        assertEquals(3, pacmanBoard.getGhostSpawn().getX());
        assertEquals(3, pacmanBoard.getGhostSpawn().getY());
    }
    //walls

    @Test
    public void testGetPacmanSpawn() {
        PacmanBoard pacmanBoard = new PacmanBoard(5,5);
        Position position = new Position(2, 2);
        pacmanBoard.setEntry(position, BoardItem.PACMAN_SPAWN);
        assertEquals(BoardItem.PACMAN_SPAWN, pacmanBoard.getEntry(position));
        assertEquals(2, pacmanBoard.getPacmanSpawn().getX());
        assertEquals(2, pacmanBoard.getPacmanSpawn().getY());
    }

    @Test
    public void testIsEmpty() {
        int counter=0;
        PacmanBoard pacmanBoard = new PacmanBoard(5, 5);
        Position position = new Position(3, 3);
        for (int i = 0; i < pacmanBoard.getWidth(); i++) {
            for (int j = 0; j < pacmanBoard.getHeight(); j++) {
                Position positionIt = new Position(i, j);
                assert(!pacmanBoard.getEntry(positionIt).equals(BoardItem.DOT) &&
                        !pacmanBoard.getEntry(positionIt).equals(BoardItem.BIG_DOT));
            }
        }
        assertEquals(true, pacmanBoard.isEmpty());

        pacmanBoard.setEntry(position, BoardItem.DOT);
        for (int i = 0; i < pacmanBoard.getWidth(); i++) {
            for (int j = 0; j < pacmanBoard.getHeight(); j++) {
                Position positionIt = new Position(i, j);
                if (pacmanBoard.getEntry(position).equals(BoardItem.BIG_DOT) ||
                        pacmanBoard.getEntry(positionIt).equals(BoardItem.DOT)) {
                    counter++;
                }
            }
        }
        assertEquals(1, counter);
        assertEquals(false, pacmanBoard.isEmpty());
    }

    @Test
    public void testReset() {
        PacmanBoard pacmanBoard = new PacmanBoard(5, 5);
        Position position = new Position(2, 2);
        Position position1 = new Position(2, 1);
        assertEquals(BoardItem.NONE, pacmanBoard.getEntry(position1));
        assertEquals(BoardItem.NONE, pacmanBoard.getEntry(position));
        pacmanBoard.setEntry(position, BoardItem.BIG_DOT_SPAWN);
        assertEquals(BoardItem.BIG_DOT_SPAWN, pacmanBoard.getEntry(position));

        pacmanBoard.reset();
        assertEquals(BoardItem.BIG_DOT, pacmanBoard.getEntry(position));
        assertEquals(BoardItem.DOT, pacmanBoard.getEntry(position1));
    }

    @Test
    public void testEatDot() {
        PacmanBoard pacmanBoard = new PacmanBoard(5, 5);
        try {
            pacmanBoard.eatDot(null);
            fail();
        } catch (NullPointerException e) {

        }
        try {
            pacmanBoard.eatDot(new Position(6, 2));
            fail();
        } catch (IndexOutOfBoundsException e) {

        }
        pacmanBoard.reset();
        Position position1 = new Position(3, 3);
        Position position2 = new Position(2, 2);
        pacmanBoard.setEntry(position2, BoardItem.BIG_DOT);
        pacmanBoard.setEntry(position1, BoardItem.GHOST_SPAWN);
        assertEquals(BoardItem.BIG_DOT, pacmanBoard.getEntry(position2));
        assertEquals(BoardItem.GHOST_SPAWN, pacmanBoard.getEntry(position1));

        pacmanBoard.eatDot(position2);
        assertEquals(BoardItem.BIG_DOT_SPAWN, pacmanBoard.getEntry(position2));

        pacmanBoard.eatDot(position1);
        assertEquals(BoardItem.GHOST_SPAWN, pacmanBoard.getEntry(position1));
    }
}

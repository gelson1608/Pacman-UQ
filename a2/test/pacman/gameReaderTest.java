package pacman;

import static org.junit.Assert.*;
import org.junit.Test;
import pacman.board.BoardItem;
import pacman.game.GameReader;
import pacman.game.PacmanGame;
import pacman.ghost.Blinky;
import pacman.ghost.Ghost;
import pacman.ghost.Inky;
import pacman.ghost.Pinky;
import pacman.util.Position;
import pacman.util.UnpackableException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class gameReaderTest {

    @Test
    public void testReader() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output"));
            PacmanGame game = GameReader.read(reader);
            reader.close();
            assertEquals(4, game.getBoard().getHeight());
            assertEquals(4, game.getBoard().getWidth());
            for (int i = 0; i < game.getBoard().getWidth(); i++) {
                assertEquals(BoardItem.WALL, game.getBoard().getEntry(new Position(i,0)));
                assertEquals(BoardItem.WALL, game.getBoard().getEntry(new Position(i,game.getBoard().getHeight() - 1)));
            }
            for (int j = 1; j < 3; j++) {
                assertEquals(BoardItem.WALL, game.getBoard().getEntry(new Position(0,j)));
                assertEquals(BoardItem.WALL, game.getBoard().getEntry(new Position(3,j)));
            }
            for (int j = 1; j < 3; j++) {
                assertEquals(BoardItem.NONE, game.getBoard().getEntry(new Position(1,j)));
                assertEquals(BoardItem.NONE, game.getBoard().getEntry(new Position(2,j)));
            }
        } catch (IOException e) {
            System.out.println("Error opening file");
            fail();
        } catch (UnpackableException e) {
            System.out.println("Invalid saved data" + e.getMessage());
            //fail();
        }
    }

    @Test
    public void testDefaultMap() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("default.map"));
            PacmanGame game = GameReader.read(reader);
            reader.close();
            assertEquals(25, game.getBoard().getWidth());
            assertEquals(9, game.getBoard().getHeight());
            assertEquals("Default CSSE2002 PacMan Map", game.getTitle());
            assertEquals("Evan Hughes", game.getAuthor());
            assertEquals(5, game.getLives());
            assertEquals(2, game.getLevel());
            assertEquals(123, game.getScores().getScore());
            assertEquals("1,1,LEFT,20,PHIL", game.getHunter().toString());
            for (Ghost g : game.getGhosts()) {
                if (g instanceof Blinky) {
                    assertEquals("3,6,UP,FRIGHTENED:15", g.toString());

                } else if(g instanceof Inky) {
                    assertEquals("1,6,UP,SCATTER:7", g.toString());

                } else if(g instanceof Pinky) {
                    assertEquals("8,6,UP,FRIGHTENED:15", g.toString());

                } else {
                    assertEquals("6,4,UP,CHASE:4", g.toString());
                }
            }
            List<String> scores = new ArrayList<>();
            scores.add("A : 0");
            scores.add("B : 5");
            scores.add("C : 100");
            for (int i = 0; i < game.getScores().getEntriesByName().size(); i++) {
                assertEquals(scores.get(i), game.getScores().getEntriesByName().get(i));
            }
            String expectedBoard = "XXXXXXXXXXXXXXXXXXXXXXXXX" + System.lineSeparator() +
                    "X10000000000000000000000X" + System.lineSeparator() +
                    "X00000B00000X000000B0000X" + System.lineSeparator() +
                    "X00000000000X00000000000X" + System.lineSeparator() +
                    "X0000000XXXXXXXXX0000000X" + System.lineSeparator() +
                    "X00000000000X00000000000X" + System.lineSeparator() +
                    "X00000B000000000000B0000X" + System.lineSeparator() +
                    "XP0000000000X0000000000$X" + System.lineSeparator() +
                    "XXXXXXXXXXXXXXXXXXXXXXXXX";
            String actualBoard = "";
            for (int i = 0; i < game.getBoard().getHeight(); i++) {
                for (int j = 0; j < game.getBoard().getWidth(); j++) {
                    actualBoard = actualBoard +
                            game.getBoard().getEntry(new Position(j, i)).getChar();
                }
                if (i < game.getBoard().getHeight() - 1){
                    actualBoard = actualBoard + System.lineSeparator();
                }
            }
            assertEquals(expectedBoard, actualBoard);
        } catch (IOException e) {
            System.out.println("Error opening file");
            fail();
        } catch (UnpackableException e) {
            System.out.println("Invalid saved data" + e.getMessage());
            fail();
        }
    }

    @Test
    public void testBadMap() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("bad.map"));
            PacmanGame game = GameReader.read(reader);
            reader.close();
            fail();
        } catch (IOException e) {
            System.out.println("Error opening file");
            fail();
        } catch (UnpackableException e) {
            System.out.println("Invalid saved data" + e.getMessage());
            //fail();
        }
    }

    @Test
    public void testBadMap2() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("bad2.map"));
            PacmanGame game = GameReader.read(reader);
            reader.close();
            fail();
        } catch (IOException e) {
            System.out.println("Error opening file");
            fail();

        } catch (UnpackableException e) {
            System.out.println("Invalid saved data" + e.getMessage());
            //fail();
        }
    }

}

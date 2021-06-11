package pacman;

import org.junit.Test;
import static org.junit.Assert.*;
import pacman.board.PacmanBoard;
import pacman.game.GameWriter;
import pacman.game.PacmanGame;
import pacman.hunter.Hunter;
import pacman.hunter.Phil;

import java.io.*;


public class gameWriterTest {

    @Test
    public void testPreconditions() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output"));
            PacmanBoard boardY = new PacmanBoard(4, 4);
            Hunter hunter = new Phil();
            String title = "First Game";
            String author = "Leo";
            PacmanGame pacmanGame = new PacmanGame(title, author, hunter, boardY);
            if(pacmanGame.getTitle() == null || pacmanGame.getAuthor() == null ||
                    pacmanGame.getHunter() == null || pacmanGame.getBoard() == null) {
                fail();
            }
            GameWriter.write(writer, pacmanGame);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }

    @Test
    public void testWriter() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output"));
            PacmanBoard boardY = new PacmanBoard(4, 4);
            Hunter hunter = new Phil();
            PacmanGame pacmanGame = new PacmanGame("First game",
                    "Leo", hunter, boardY);
            GameWriter.write(writer, pacmanGame);
            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader("output"));
            String strFull = "";
            String str2;
            while ((str2 = reader.readLine()) != null) {
                strFull = strFull + str2 + System.lineSeparator();
            }
            strFull = strFull.substring(0, strFull.length() - 1);
            StringWriter str = new StringWriter();
            str.write("[Board]" + System.lineSeparator() + "4,4" +
                    System.lineSeparator() + "XXXX" +
                    System.lineSeparator() + "X00X" + System.lineSeparator() +
                    "X00X" + System.lineSeparator() + "XXXX" + System.lineSeparator() +
                    System.lineSeparator() + "[Game]" + System.lineSeparator() +
                    "title = First game" + System.lineSeparator() + "author = Leo" +
                    System.lineSeparator() + "lives = 4" + System.lineSeparator() +
                    "level = 0" + System.lineSeparator() + "score = 0" +
                    System.lineSeparator() + "hunter = 0,0,UP,0,PHIL" +
                    System.lineSeparator() + "blinky = 0,0,UP,SCATTER:10" +
                    System.lineSeparator() + "inky = 0,0,UP,SCATTER:10" +
                    System.lineSeparator() + "pinky = 0,0,UP,SCATTER:10" +
                    System.lineSeparator() + "clyde = 0,0,UP,SCATTER:10" +
                    System.lineSeparator() + System.lineSeparator() + "[Scores]");
            assertEquals(str.toString(), strFull);
        } catch (IOException e) {
            fail();
        }
    }
}

package pacman.game;

import java.io.IOException;
import java.io.Writer;

public class GameWriter {

    public GameWriter() {

    }

    /**
     * Saves a PacmanGame to a writer using the following rules:
     * The first line of the file will be the Board block header: "[Board]".
     * Following this on the line below will be the width and height comma
     * separated with no leading zeros and no spaces. After this on the next
     * line is the Game Board which is to be the toString representation of the
     * board.
     *
     * One blank line.
     * On the next line is the "[Game]" block which will output the following
     * assignments in order ( title, author, lives, level, score, hunter,
     * blinky, inky, pinky, clyde ). The assignments are to have a single space
     * before and after the equals sign. The assignments for ( hunter, blinky,
     * inky, pinky, clyde) are to be the toString representation of these
     * entities. Each assignment is to be on its own line.
     *
     * One blank line.
     * The last block is the "[Scores]" block which should be output as a
     * multiline list of the scores where the name and value of the score are
     * separated by a ":". The scores should be output sorted by Name
     * ScoreBoard.getEntriesByName(). The last score should not have a newline.
     *
     * Note: All integers are to have no leading zeros.
     * @param writer to output the data to.
     * @param game to encode into the save data format.
     * @throws IOException during an issue with saving to the file.
     */
    public static void write(Writer writer, PacmanGame game) throws IOException {
        int listSize = game.getScores().getEntriesByName().size();
        writer.write("[Board]" + System.lineSeparator());
        writer.write(game.getBoard().getWidth() + "," +
                game.getBoard().getHeight());
        writer.write(game.getBoard().toString() + System.lineSeparator()
                + System.lineSeparator());
        writer.write("[Game]" + System.lineSeparator());
        writer.write("title = " + game.getTitle() + System.lineSeparator()
                + "author = " + game.getAuthor() + System.lineSeparator()
                + "lives = " + game.getLives() + System.lineSeparator()
                + "level = " + game.getLevel() + System.lineSeparator()
                + "score = " + game.getScores().getScore() + System.lineSeparator());
        writer.write("hunter = " + game.getHunter().toString()
                + System.lineSeparator());
        writer.write("blinky = " + game.getGhosts().get(0).toString()
                + System.lineSeparator());
        writer.write("inky = " + game.getGhosts().get(1).toString()
                + System.lineSeparator());
        writer.write("pinky = " + game.getGhosts().get(2).toString()
                + System.lineSeparator());
        writer.write("clyde = " + game.getGhosts().get(3).toString()
                + System.lineSeparator() + System.lineSeparator());
        writer.write("[Scores]" + System.lineSeparator());
        //if (listSize > 0) {
        //    writer.write(System.lineSeparator());
        //}
        for (int i = 0; i < listSize; i++) {
            writer.write(game.getScores().getEntriesByName().get(i));
            if (i < listSize - 1) {
                writer.write(System.lineSeparator());
            }
        }
        writer.close();
    }
}

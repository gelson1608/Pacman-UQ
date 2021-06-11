package pacman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.board.PacmanBoard;
import pacman.display.ScoreViewModel;
import pacman.game.PacmanGame;
import pacman.hunter.Hungry;
import pacman.hunter.Hunter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreViewModelTest {
    @Test
    public void testGetScores(){
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game10", "Leo",
                hunter, pacmanBoard);
        pacmanGame.getScores().setScore("Luis", 123);
        pacmanGame.getScores().setScore("Nibe", 346);
        ScoreViewModel viewModel = new ScoreViewModel(pacmanGame);
        ObservableList<String> listByName = FXCollections.observableArrayList(pacmanGame.getScores().getEntriesByName());
        ObservableList<String> listByScore = FXCollections.observableArrayList(pacmanGame.getScores().getEntriesByScore());
        assertEquals(listByName,viewModel.getScores());
        viewModel.switchScoreOrder();
        viewModel.update();
        assertEquals(listByScore,viewModel.getScores());
        viewModel.switchScoreOrder();
        viewModel.update();
        assertEquals(listByName, viewModel.getScores());
    }

    @Test
    public void testGetCurrentScoreProperty() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game30", "Leo",
                hunter, pacmanBoard);
        ScoreViewModel viewModel = new ScoreViewModel(pacmanGame);
        assertEquals("Score: 0", viewModel.getCurrentScoreProperty().getValue());
        pacmanGame.getScores().increaseScore(123456);
        viewModel.update();
        assertEquals("Score: 123456", viewModel.getCurrentScoreProperty().getValue());
    }

    @Test
    public void testGetSortedBy() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game40", "Leo",
                hunter, pacmanBoard);
        ScoreViewModel viewModel = new ScoreViewModel(pacmanGame);
        assertEquals("Sorted by Name", viewModel.getSortedBy().getValue());
        viewModel.switchScoreOrder();
        viewModel.update();
        assertEquals("Sorted by Score", viewModel.getSortedBy().getValue());
    }

    @Test
    public void testGetCurrentScore() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game50", "Leo",
                hunter, pacmanBoard);
        ScoreViewModel viewModel = new ScoreViewModel(pacmanGame);
        assertEquals(0, viewModel.getCurrentScore());
        pacmanGame.getScores().increaseScore(123456);
        assertEquals(123456, viewModel.getCurrentScore());
    }

    @Test
    public void testSetPlayerScore() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game60", "Leo",
                hunter, pacmanBoard);
        pacmanGame.getScores().setScore("Luis", 123);
        pacmanGame.getScores().setScore("Nibe", 346);
        List<String> scoreListName = new ArrayList<>();
        scoreListName.add("Luis : 123");
        scoreListName.add("Nibe : 346");
        ScoreViewModel viewModel = new ScoreViewModel(pacmanGame);
        for (int i = 0; i < scoreListName.size(); i++) {
            assertEquals(scoreListName.get(i), viewModel.getScores().get(i));
        }
        viewModel.setPlayerScore("Carlos", 888);
        scoreListName.add("Carlos : 888");
        Collections.sort(scoreListName);
        viewModel.update();
        for (int i = 0; i < scoreListName.size(); i++) {
            assertEquals(scoreListName.get(i), viewModel.getScores().get(i));
        }
    }

    @Test
    public void testUpdate() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game20", "Leo",
                hunter, pacmanBoard);
        pacmanGame.getScores().setScore("Luis", 123);
        pacmanGame.getScores().setScore("Nibe", 346);
        ScoreViewModel viewModel = new ScoreViewModel(pacmanGame);
        ObservableList<String> listByName = FXCollections.observableArrayList(pacmanGame.getScores().getEntriesByName());
        ObservableList<String> listByScore = FXCollections.observableArrayList(pacmanGame.getScores().getEntriesByScore());
        assertEquals(listByName,viewModel.getScores());
        assertEquals("Sorted by Name", viewModel.getSortedBy().getValue());
        assertEquals("Score: 0", viewModel.getCurrentScoreProperty().getValue());
        pacmanGame.getScores().increaseScore(1997);
        viewModel.switchScoreOrder();
        viewModel.update();
        assertEquals(listByScore,viewModel.getScores());
        assertEquals("Sorted by Score", viewModel.getSortedBy().getValue());
        assertEquals("Score: 1997", viewModel.getCurrentScoreProperty().getValue());
        viewModel.switchScoreOrder();
        viewModel.update();
        assertEquals(listByName, viewModel.getScores());
        assertEquals("Sorted by Name", viewModel.getSortedBy().getValue());
        assertEquals("Score: 1997", viewModel.getCurrentScoreProperty().getValue());
    }
}

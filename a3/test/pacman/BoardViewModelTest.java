package pacman;

import org.junit.Test;
import static org.junit.Assert.*;
import pacman.board.PacmanBoard;
import pacman.display.BoardViewModel;
import pacman.game.PacmanGame;
import pacman.hunter.*;
import pacman.util.Direction;
import pacman.util.Position;

public class BoardViewModelTest {
    @Test
    public void testGetLives() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game1", "Leo",
                hunter, pacmanBoard);
        pacmanGame.setLives(10);
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        assertEquals(10, boardViewModel.getLives());
    }

    @Test
    public void testGetLevel() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game2", "Leo",
                hunter, pacmanBoard);
        pacmanGame.setLevel(2);
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        assertEquals(2, boardViewModel.getLevel());
    }

    @Test
    public void testGetPacmanColour() {
        Hunter hunter = new Phil();
        Hunter hunter2 = new Phasey();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game3", "Leo",
                hunter, pacmanBoard);
        pacmanGame.setLives(10);
        pacmanGame.setLevel(2);
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        assertEquals("#FFE709", boardViewModel.getPacmanColour());
        pacmanGame.getHunter().activateSpecial(20);
        assertEquals("#FFE709", boardViewModel.getPacmanColour());

        PacmanGame pacmanGame2 = new PacmanGame("Game4", "Leo",
                hunter2, pacmanBoard);
        pacmanGame2.setLives(10);
        pacmanGame2.setLevel(2);
        BoardViewModel boardViewModel2 = new BoardViewModel(pacmanGame2);
        assertEquals("#FFE709", boardViewModel2.getPacmanColour());
        pacmanGame2.getHunter().activateSpecial(20);
        assertEquals("#CDC3FF", boardViewModel2.getPacmanColour());
    }

    @Test
    public void testGetPacmanMouthAngle() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game5", "Leo",
                hunter, pacmanBoard);
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        hunter.setDirection(Direction.LEFT);
        assertEquals(210, boardViewModel.getPacmanMouthAngle());
        hunter.setDirection(Direction.RIGHT);
        assertEquals(30, boardViewModel.getPacmanMouthAngle());
        hunter.setDirection(Direction.UP);
        assertEquals(120, boardViewModel.getPacmanMouthAngle());
        hunter.setDirection(Direction.DOWN);
        assertEquals(300, boardViewModel.getPacmanMouthAngle());
    }

    @Test
    public void testGetPacmanPosition() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game1", "Leo",
                hunter, pacmanBoard);
        hunter.setPosition(new Position(5,5));
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        assertEquals(new Position(5,5), boardViewModel.getPacmanPosition());
    }

    @Test
    public void testGetBoard() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game1", "Leo",
                hunter, pacmanBoard);
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        assertEquals(pacmanBoard, boardViewModel.getBoard());
    }

    @Test
    public void testGetGhosts() {
        Hunter hunter = new Hungry();
        PacmanBoard pacmanBoard = new PacmanBoard(10,10);
        PacmanGame pacmanGame = new PacmanGame("Game1", "Leo",
                hunter, pacmanBoard);
        BoardViewModel boardViewModel = new BoardViewModel(pacmanGame);
        for (int i = 0; i < pacmanGame.getGhosts().size(); i++) {
            assertEquals(pacmanGame.getGhosts().get(i).getPosition(),
                    boardViewModel.getGhosts().get(i).getKey());
            assertEquals(pacmanGame.getGhosts().get(i).getColour(),
                    boardViewModel.getGhosts().get(i).getValue());
        }
    }
}

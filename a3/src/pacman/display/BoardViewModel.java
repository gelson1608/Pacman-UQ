package pacman.display;

import javafx.util.Pair;
import pacman.board.PacmanBoard;
import pacman.game.PacmanGame;
import pacman.ghost.Ghost;
import pacman.ghost.Phase;
import pacman.hunter.Phil;
import pacman.util.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardViewModel {

    private PacmanGame model;

    /**
     * Constructs a new BoardViewModel to model the given PacmanGame.
     * @param model the game to be played
     */
    public BoardViewModel(PacmanGame model) {

        this.model = model;

    }

    //We return the lives of the hunter in the model(game).
    /**
     * Returns the number of lives left for the player in the game.
     * @return the number of lives.
     */
    public int getLives() {

        return model.getLives();

    }

    //We return the level of the game.
    /**
     * Returns the current level that the player is on.
     * @return the current level of the game.
     */
    public int getLevel() {

        return model.getLevel();

    }

    //We return the colour of the pacman depending on if the hunter's special
    //is active. If the Hunter is of type Phil, the colour remains the same.
    /**
     * Returns a colour string to represent how the hunter should be displayed.
     * If game's hunter special is active it should return "#CDC3FF", otherwise
     * return "#FFE709".
     * @return the colour associated with the game's hunter.
     */
    public String getPacmanColour() {

        if (model.getHunter().getSpecialDurationRemaining() > 0) {
            if ((model.getHunter() instanceof Phil)){
                return "#FFE709";
            }
            return "#CDC3FF";
        } else {
            return "#FFE709";
        }
    }

    //We return the mouth angle for the pacman depending on its current direction.
    /**
     * Returns the starting angle of the mouth arc of the pacman depending on
     * its direction. If the hunter's direction is RIGHT, return 30.
     * If the hunter's direction if UP, return 120.
     * If the hunter's direction is LEFT, return 210.
     * If the hunter's direction is DOWN, return 300.
     * @return the angle based on the direction of the game's hunter.
     */
    public int getPacmanMouthAngle() {
        switch (model.getHunter().getDirection()) {
            case DOWN:
                return 300;
            case RIGHT:
                return 30;
            case LEFT:
                return 210;
            case UP:
                return 120;
                default:
                    return -1;
        }
    }

    //We return the pacman's current position
    /**
     * Returns the current position of the game's hunter.
     * @return the position of the hunter.
     */
    public Position getPacmanPosition() {

        return model.getHunter().getPosition();

    }

    //We return the model's board
    /**
     * Returns the board associated with the game.
     * @return the game board.
     */
    public PacmanBoard getBoard() {

        return model.getBoard();

    }

    //We return the positions and colours of the ghosts. If the ghost is in
    //frightened phase then it will have a different colour than the one it
    //usually has.
    /**
     * Returns the positions and colours of the ghosts in the game. Each ghost
     * should be represented as a Pair(position, colour), where position is
     * their current position on the board, and colour is their colour. The
     * ghost's colour should be given be by Ghost.getColour(). However, if the
     * ghost's phase is FRIGHTENED, this colour should instead be "#0000FF".
     * @return a list of Pair<position, colour > representing the ghosts in the
     * game, in no particular order.
     */
    public List<Pair<Position, String>> getGhosts() {
        Pair<Position, String> ghostInfo;
        List<Pair<Position, String>> ghostsList = new ArrayList<>();
        for (int i = 0; i < model.getGhosts().size(); i++) {
            Ghost ghost = model.getGhosts().get(i);

            if (ghost.getPhase().equals(Phase.FRIGHTENED)) {
                ghostInfo = new Pair<>(ghost.getPosition(), "#0000FF");
            } else {
                ghostInfo = new Pair<>(ghost.getPosition(), ghost.getColour());
            }

            ghostsList.add(ghostInfo);
        }
        return ghostsList;
    }

}

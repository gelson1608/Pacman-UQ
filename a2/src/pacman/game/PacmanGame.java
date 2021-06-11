package pacman.game;

import pacman.board.PacmanBoard;
import pacman.ghost.*;
import pacman.hunter.Hunter;
import pacman.score.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class PacmanGame {
    private String title;
    private String author;
    private Hunter hunter;
    private PacmanBoard pacmanBoard;
    private int tick, level, lives;
    private Ghost pinkyGhost, blinkyGhost, inkyGhost, clydeGhost;
    private ScoreBoard scoreBoard;

    /**
     * Creates a new game with the given parameters and spawns one of each
     * type of ghost (Blinky, Clyde, Inky, Pinky). The ghosts should be spawned
     * at the ghost spawn point.
     * @param title of the game board.
     * @param author of the game board.
     * @param hunter for the current game.
     * @param board to be copied for this game.
     */
    public PacmanGame(String title, String author, Hunter hunter, PacmanBoard board) {
        this.title = title;
        this.author = author;
        this.hunter = hunter;
        this.pacmanBoard = new PacmanBoard(board);
        this.tick = 0;
        this.level = 0;
        this.lives = 4;
        this.scoreBoard = new ScoreBoard();
        this.pinkyGhost = new Pinky();
        this.blinkyGhost = new Blinky();
        this.inkyGhost = new Inky();
        this.clydeGhost = new Clyde();
        clydeGhost.setPosition(pacmanBoard.getGhostSpawn());
        pinkyGhost.setPosition(pacmanBoard.getGhostSpawn());
        blinkyGhost.setPosition(pacmanBoard.getGhostSpawn());
        inkyGhost.setPosition(pacmanBoard.getGhostSpawn());
    }

    /**
     * @return title of the map.
     * @ensures result != null.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return author of the map.
     * @ensures result != null.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Gets the current pacman board.
     * @return a mutable reference to the board.
     */
    public PacmanBoard getBoard() {
        return this.pacmanBoard;
    }

    /**
     * Gets the number of times that tick has been called in the current game.
     * @return the current game tick value.
     */
    public int getTick() {
        return this.tick;
    }

    /**
     * Gets the current score board.
     * @return a mutable reference to the score board.
     */
    public ScoreBoard getScores() {
        return this.scoreBoard;
    }

    /**
     * @return current level of the game.
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Sets the level of the game
     * @param level to be set
     * @ensures newLevel = max(0, givenLevel)
     */
    public void setLevel(int level) {
        if (level > this.level) {
            this.level = level;
        }
    }

    /**
     * @return amount of lives the player currently has.
     */
    public int getLives() {
        return  this.lives;
    }

    /**
     * Sets the lives of the current player
     * @param lives to be set
     * @ensures newLives = max(0, givenLives)
     */
    public void setLives(int lives) {
        if (lives > this.lives) {
            this.lives = lives;
        }
    }

    /**
     * @return a mutable reference to the hunter
     */
    public Hunter getHunter() {
        return this.hunter;
    }

    /**
     * Note: modifying the returned list shouldn't affect the internal
     * state of PacmanGame.
     * @return a list of ghosts in the game
     */
    public List<Ghost> getGhosts() {
        List<Ghost> ghostsList = new ArrayList<>();
        ghostsList.add(blinkyGhost);
        ghostsList.add(inkyGhost);
        ghostsList.add(pinkyGhost);
        ghostsList.add(clydeGhost);
        return ghostsList;
    }

    /**
     * Tick If we do not have any lives (getLives() == 0) then do nothing.
     * Otherwise we do the following in this order:
     * The Hunter moves Hunter.move(PacmanGame).
     * For each ghost in the game, call Hunter.hit(Ghost)
     * The Ghosts that are alive move on even ticks Ghost.move(PacmanGame)
     * getTick().
     * For each Ghost in the game, call Hunter.hit(Ghost) on the game's hunter.
     * For each ghost which is dead:
     * Reset the ghost.
     * Set the ghost's position to the ghost spawn position on the current
     * board.
     * Add 200 points to the score.
     * If the hunter is dead, then decrease the lives and reset all the entities
     * and place them at their spawn points.
     * If the board is empty, then increase the level and set the ticks to 0 and
     * reset the board and entities placing them at their spawn points.
     * If we did not increase the level then increase the tick value.
     * Note: game should start at a tick count of zero.
     */
    public void tick() {
        if(getLives()!=0) {
            int initialLevel = getLevel();
            hunter.move(this);
            for (Ghost i: getGhosts()) {
                hunter.hit(i);
                if (getTick() % 2 == 0 && !i.isDead()) {
                    i.move(this);
                }
                hunter.hit(i);
                if (i.isDead()) {
                    i.reset();
                    i.setPosition(getBoard().getGhostSpawn());
                    getScores().increaseScore(200);
                }
            }
            if (hunter.isDead()) {
                lives--;
                resetEntities();
            }
            if (getBoard().isEmpty()) {
                level++;
                tick=0;
                getBoard().reset();
                resetEntities();
            }
            if (getLevel() == initialLevel) {
                tick++;
            }
        }
    }

    /**
     * Resets each entity in the game and places them in their spawn positions.
     */
    private void resetEntities() {
        for (Ghost i : getGhosts()) {
            i.reset();
            i.setPosition(getBoard().getGhostSpawn());

        }
        hunter.reset();
        hunter.setPosition(getBoard().getPacmanSpawn());
    }

    /**
     * Resets the Game in the following way:
     * Lives is set to the default of 4.
     * Level is set to 0.
     * ScoreBoard is reset ScoreBoard.reset()
     * PacmanBoard is reset PacmanBoard.reset()
     * All entities are reset
     * All entity positions are set to their spawn locations.
     * The tick value is reset to zero.
     */
    public void reset() {
        lives=4;
        level=0;
        getScores().reset();
        getBoard().reset();
        resetEntities();
        tick=0;
    }

    /**
     * For each ghost in the game, set its phase to be Phase.FRIGHTENED with a
     * duration of Phase.FRIGHTENED.getDuration();
     */
    public void setGhostsFrightened() {
        for (Ghost i : getGhosts()) {
            i.setPhase(Phase.FRIGHTENED, Phase.FRIGHTENED.getDuration());
        }
    }
}

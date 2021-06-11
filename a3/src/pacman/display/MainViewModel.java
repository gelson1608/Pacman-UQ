package pacman.display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pacman.game.GameWriter;
import pacman.game.PacmanGame;
import pacman.hunter.Hunter;
import pacman.util.Direction;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MainViewModel {

    private PacmanGame model;
    private String saveFileName;
    private ScoreViewModel scoreVM;
    private BoardViewModel boardVM;
    private int tick;
    private StringProperty title;
    private BooleanProperty gameOver, paused;

    //We initiallize the values for the Simple properties variables
    //We set tick to 0 and we assign the parameters to its variables
    //we also initiallize the score view model and board view model.
    /**
     * Creates a MainViewModel and updates the properties and the ScoreViewModel created.
     * ScoreViewModel and BoardViewModel should be created here.
     * By default, the game should be paused.
     * @param model the PacmanGame to link to the view.
     * @param saveFileName the name for saving the game.
     */
    public MainViewModel(PacmanGame model, String saveFileName) {
        this.tick = 0;
        this.gameOver = new SimpleBooleanProperty(false);
        this.title = new SimpleStringProperty(model.getTitle() +
                " by " + model.getAuthor());
        this.paused = new SimpleBooleanProperty(true);
        this.model = model;
        this.saveFileName = saveFileName;
        scoreVM = new ScoreViewModel(model);
        boardVM = new BoardViewModel(model);
    }

    //We update the value of the title property and then we call the update
    //method for the score view model.
    /**
     * Updates the title of the game window and the score view model.
     */
    public void update() {
        title.setValue(model.getTitle() + " by " + model.getAuthor());
        scoreVM.update();
    }

    //We return the title property with the value updated in the update() method.
    /**
     * Gets the title property of the Game Window.
     * @return the title property of the game
     */
    public StringProperty getTitle() {
        return title;
    }

    //We return the game over property with the value updated in the tick() method.
    /**
     * Gets the property representing whether the game is over or not.
     * @return the game over status.
     */
    public BooleanProperty isGameOver() {
        return gameOver;
    }

    //We write the current state of the game to a file, so that we can come back
    //to that state by reading it in the future.
    /**
     * Saves the current state of the game to the file location given in the
     * constructor. An IOException should not cause the program to crash and
     * should be ignored.
     */
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFileName));
            GameWriter.write(writer, model);
            writer.close();
        } catch (Exception e) {
            //do nothing
        }
    }

    //Here we determine the pace of the game, we increase the speed of the game
    //approximately every two levels so that it gets challenging for the user.
    //We constantly increase the value of the tick variable declared at the
    //beginning to achieve the increase of speed, and when the player's lives
    //get to 0, then is game over.
    /**
     * Tick is to be called by the view at around 60 times a second. This method
     * will pass these ticks down to the model at a reduced rate depending on
     * the level of the game. The game starts with zero ticks.
     * If the game is not paused:
     * Check if the current tick count is integer-divisible by the delay
     * specified for the current level. If it is integer-divisible:
     * Tick the model. See PacmanGame.tick()
     * Increment the tick count by 1.
     * Finally, update the "game over" property to be true if the player
     * currently has 0 lives left, and false otherwise. This should be done
     * regardless of whether or not the game is paused.
     */
    public void tick() {

        if (!paused.get()) {
            if (model.getLevel() == 0 || model.getLevel() == 1) {
                if (tick % 50 == 0) {
                    model.tick();
                }
            } else if (model.getLevel() == 2 || model.getLevel() == 3) {
                if (tick % 40 == 0) {
                    model.tick();
                }
            } else if (model.getLevel() == 4 || model.getLevel() == 5) {
                if (tick % 30 == 0) {
                    model.tick();
                }
            } else if (model.getLevel() == 6 || model.getLevel() == 7 ||
                        model.getLevel() == 8) {
                if (tick % 20 == 0) {
                    model.tick();
                }
            } else {
                if (tick % 10 == 0) {
                    model.tick();
                }
            }
            tick = tick + 2;
        }

        if (model.getLives() == 0) {
            gameOver.set(true);
        }
    }

    //Here we manage the input from the keyboard:
    //if the game is paused, then the user is only allowed to press pause again
    //or to reset the game.
    //if the game is not paused then they are free to press any of the keys
    //that do something (P,O,R,A,S,D,W)
    //The player moves the pacman with (A, S, D, W) pauses the game
    //with P and activates the hunter special ability with O
    //When we pause the game, we update the value of the paused property
    /**
     * Accepts key input from the view and acts according to the key.
     * @param input incoming input from the view.
     */
    public void accept(String input) {
        if (paused.get()) {
            if (input.equalsIgnoreCase("p")) {
                paused.setValue(false);
            } else if (input.equalsIgnoreCase("r")) {
                model.reset();
            }
        } else if (!paused.get()) {
            if (input.equalsIgnoreCase("p")) {
                paused.setValue(true);
            } else if (input.equalsIgnoreCase("r")) {
                model.reset();
            } else if (input.equalsIgnoreCase("a")) {
                model.getHunter().setDirection(Direction.LEFT);
            } else if (input.equalsIgnoreCase("d")) {
                model.getHunter().setDirection(Direction.RIGHT);
            } else if (input.equalsIgnoreCase("w")) {
                model.getHunter().setDirection(Direction.UP);
            } else if (input.equalsIgnoreCase("s")) {
                model.getHunter().setDirection(Direction.DOWN);
            } else if (input.equalsIgnoreCase("o")) {
                model.getHunter().activateSpecial(Hunter.SPECIAL_DURATION);
            }
        }
    }

    //We return the paused property with the value updated in the accept() method.
    /**
     * Gets the paused property of the game.
     * @return the property associated with the pause state.
     */
    public BooleanProperty isPaused() {

        return paused;

    }

    //We return the score view model assigned in the constructor
    /**
     * Gets the ScoreViewModel created at initialisation.
     * @return the ScoreViewModel associated wtih the game's scores.
     */
    public ScoreViewModel getScoreVM() {

        return scoreVM;
    }

    //We return the board view model assigned in the constructor
    /**
     * Gets the BoardViewModel created at initialisation.
     * @return the BoardViewModel associated with the game play.
     */
    public BoardViewModel getBoardVM() {

        return boardVM;
    }
}

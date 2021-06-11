package pacman.display;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pacman.game.PacmanGame;

public class ScoreViewModel {

    private ObservableList<String> scores;  //List of scores to display on screen
    private StringProperty currentScore;    //Current score of player to display
    private StringProperty sortedBy;        //Tag shows how the scores are ordered
    private int sortOrder;                  //Variable the represents the change
    private PacmanGame model;               //of order for the scores to display


    //We initiallize the values for the Simple properties variables
    //We set sortOrder to 0 which represents the scores ordered by name,
    //and ordered by scores otherwise.
    /**
     * Creates a new ScoreViewModel and updates its properties. The default
     * ordering of the scores should be by name.
     * @param model the PacmanGame to link to the view
     */
    public ScoreViewModel(PacmanGame model) {
        this.model = model;
        this.sortedBy = new SimpleStringProperty("Sorted by Name");
        this.sortOrder = 0;
        this.currentScore = new SimpleStringProperty("Score: "
                + model.getScores().getScore());
        this.scores = FXCollections.observableArrayList(model.getScores().getEntriesByName());
    }

    //Here we update the values of the property variables to the current
    //values of the model (game) depending on the sortOrder variable
    //declared before.
    /**
     * Updates the properties containing the current score, the sort order of
     * the scoreboard and the list of sorted scores.
     *
     * The format for the current score property should be
     * "Score: [currentScore]"
     * without quotes or brackets, where currentScore is the value returned by
     * ScoreBoard.getScore().
     *
     * The sort order property should be set to either "Sorted by Name" or
     * "Sorted by Score", depending on the current score sort order.
     *
     * Finally, the property representing the list of scores should be updated
     * to contain a list of scores sorted according to the current score sort
     * order, as returned by ScoreBoard.getEntriesByName() and
     * ScoreBoard.getEntriesByScore().
     */
    public void update() {

        currentScore.setValue("Score: " + getCurrentScore());

        if (sortOrder == 0) {
            sortedBy.setValue("Sorted by Name");
            scores.clear();
            scores.addAll(model.getScores().getEntriesByName());
        } else {
            sortedBy.setValue("Sorted by Score");
            scores.clear();
            scores.addAll(model.getScores().getEntriesByScore());
        }

    }

    //Here we change the sortOrder from Name to Score and viceversa
    /**
     * Changes the order in which player's scores are displayed.
     * The possible orderings are by name or by score. Calling this method once
     * should switch between these two orderings.
     */
    public void switchScoreOrder() {

        if (sortOrder == 0) {
            sortOrder++;
        } else {
            sortOrder = 0;
        }

    }

    //We return the property with the value updated in the update() method.
    /**
     * Returns the StringProperty containing the current score for the player.
     * @return the property representing the current score.
     */
    public StringProperty getCurrentScoreProperty() {

        return currentScore;

    }

    //We return the property with the value updated in the update() method.
    /**
     * Returns the StringProperty of how the player's scores are displayed.
     * @return StringProperty representing how the player's scores are displayed
     */
    public StringProperty getSortedBy() {

        return sortedBy;

    }

    //We return the property with the value updated in the update() method.
    /**
     * Returns a list containing all "Name : Value" score entries in the game's
     * ScoreBoard, sorted by the current sort order.
     * @return the list of sorted scores.
     */
    public ObservableList<String> getScores() {
        return scores;
    }

    //We return the current value in the model for the score of the player
    //who is playing the game.
    /**
     * Returns the overall current score for the game.
     * @return current score.
     */
    public int getCurrentScore() {
        return model.getScores().getScore();
    }

    //We set the name and score of the player to the list of scores of the model
    /**
     * Sets the given player's score to score. This should override the score if
     * it was previously set (even if new score is lower). Invalid player names
     * should be ignored.
     * @param player the player
     * @param score the new score
     */
    public void setPlayerScore(String player, int score) {
        model.getScores().setScore(player, score);
    }
}

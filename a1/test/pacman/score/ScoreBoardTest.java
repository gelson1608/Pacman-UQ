package pacman.score;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ScoreBoardTest {

    @Test
    public void testScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard();
        assertEquals(0, scoreBoard.getScore());
    }

    @Test
    public void testSetScore(){
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setScore(null, 10);
        scoreBoard.setScore("__#@./", 20);
        scoreBoard.setScore("Lunay", -1);

        assertEquals(0, scoreBoard.getEntriesByName().size());
        assertEquals(0, scoreBoard.getEntriesByScore().size());

        scoreBoard.setScore("Luis", 20);
        scoreBoard.setScore("Carlos", 15);
        scoreBoard.setScore("Alvaro", 10);

        assertEquals(3, scoreBoard.getEntriesByName().size());
        assertEquals(3, scoreBoard.getEntriesByScore().size());

        System.out.println(scoreBoard.getEntriesByName());
        System.out.println(scoreBoard.getEntriesByScore());
    }

    @Test
    public void testIncreaseScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.increaseScore(-1);
        assertEquals(0, scoreBoard.getScore());
        scoreBoard.increaseScore(0);
        assertEquals(0, scoreBoard.getScore());
    }

    @Test
    public void testGetScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.increaseScore(200);
        assertEquals(200, scoreBoard.getScore());
    }

    @Test
    public void testReset() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.increaseScore(1000);
        assertEquals(1000, scoreBoard.getScore());
        scoreBoard.reset();
        assertEquals(0, scoreBoard.getScore());

    }

    @Test
    public void testSetScores() {
        ScoreBoard scoreBoard = new ScoreBoard();
        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<String, Integer> hashMap1 = new HashMap<>();
        hashMap.put("Luis", 20);
        hashMap.put("Carlos", 15);
        hashMap.put("Alvaro", 10);
        scoreBoard.setScores(null);
        scoreBoard.setScores(hashMap1);
        scoreBoard.setScores(hashMap);
    }

    @Test
    public void testEntriesByNames() {
        ScoreBoard scoreBoard = new ScoreBoard();
        assert(scoreBoard.getEntriesByName().isEmpty());

        scoreBoard.setScore("Luis", 20);
        scoreBoard.setScore("Carlos", 15);
        scoreBoard.setScore("Alvaro", 10);
        scoreBoard.setScore("luis", 5);

        assert(scoreBoard.getEntriesByName().size() == 4);
        scoreBoard.getEntriesByName();
        System.out.println(scoreBoard.getEntriesByName());

        for (int i = 0; i < scoreBoard.getEntriesByName().size() - 2; i++) {
            assert(scoreBoard.getEntriesByName().get(i).charAt(0) <=
                    scoreBoard.getEntriesByName().get(i + 1).charAt(0));
        }
    }

    @Test
    public void testEntriesByScore() {
        ScoreBoard scoreBoard = new ScoreBoard();
        assert(scoreBoard.getEntriesByScore().isEmpty());

        scoreBoard.setScore("Luis", 20);
        scoreBoard.setScore("Carlos", 15);
        scoreBoard.setScore("Alvaro", 10);
        scoreBoard.setScore("David", 10);
        scoreBoard.setScore("luis", 5);
        scoreBoard.setScore("Maluma", 10);
        scoreBoard.setScore("Beto", 10);


        assert(scoreBoard.getEntriesByScore().size() == 7);
        System.out.println(scoreBoard.getEntriesByScore());

        for (int i = 0; i < scoreBoard.getEntriesByScore().size() - 2; i++) {
            String numString = scoreBoard.getEntriesByScore().get(i).split(" : ", 0)[1];
            String numString2 = scoreBoard.getEntriesByScore().get(i + 1).split(" : ", 0)[1];
            int num = Integer.parseInt(numString);
            int num2 = Integer.parseInt(numString2);
            assert(num >= num2);
        }

    }

}


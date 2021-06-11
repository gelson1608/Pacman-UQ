package pacman.score;
import pacman.board.PacmanBoard;

import java.util.*;

public class ScoreBoard {
    private List<String> names;
    private List<Integer> scores;
    private Map<String, Integer> scoreMap;
    private int currentScore;

    public ScoreBoard() {
        this.currentScore = 0;
        scoreMap = new HashMap<>();
        names = new ArrayList<>();
        scores = new ArrayList<>();
    }

    public List<String> getEntriesByName() {
        Collections.sort(names);
        List<String> listedByName = new ArrayList<>();
        for (String s: names) {
            listedByName.add(s + " : " + scoreMap.get(s));
        }
        return listedByName;
    }


    public List<String> getEntriesByScore() {
        //Falta
        List<String> listedByScore = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (int i: scores) {
            for (String s: scoreMap.keySet()) {
                if (scoreMap.get(s).equals(i) && !nameList.contains(s)) {
                    listedByScore.add(s + " : " + i);
                    nameList.add(s);
                }
            }
        }
        return listedByScore;
    }

    public int getScore(){
        return this.currentScore;
    }

    public void increaseScore(int additional) {
        if (additional >= 0) {
            this.currentScore = this.currentScore + additional;
        }
    }

    public void reset(){
        this.currentScore = 0;
    }

    public void setScore(String name, int score) {
        int counter = 0;
        int counter2 = 0;
        int index = 0;
        List<Integer> splittedListA;
        List<Integer> splittedListB;
        if(name != null && score >= 0) {
            int nameLength = name.length();
            while(nameLength > 0) {
                int charCode = name.charAt(nameLength - 1);
                if(!((charCode >= 48 && charCode <= 57) ||
                        (charCode >= 65 && charCode <= 90) ||
                        (charCode >= 97 && charCode <= 122))) {
                    System.out.println("invalid character");
                    counter++;
                    break;
                }
                nameLength = nameLength - 1;
            }
            if (counter==0) {
                scoreMap.put(name, score);
                names.add(name);
                if(scores.size() >= 1) {
                    for (int i: scores) {
                        if (score > i) {
                            index = scores.indexOf(i);
                            splittedListA = scores.subList(0, index);
                            splittedListB = scores.subList(index, scores.size());
                            scores.clear();
                            scores.addAll(splittedListA);
                            scores.add(score);
                            scores.addAll(splittedListB);
                            counter2++;
                        }
                        break;
                    }
                    if (counter2 == 0) {
                        scores.add(score);
                    }
                }else scores.add(score);
            }
        }else{
            System.out.println("invalid parameters");
        }
    }

    public void setScores(Map<String, Integer> scores) {
        try {
            if (scores.isEmpty()) {
                System.out.println(" No scores found, empty map");
            } else {
                for (Map.Entry<String, Integer> entry : scores.entrySet())
                    this.setScore(entry.getKey(), entry.getValue());
            }
        } catch (NullPointerException e) {
            System.out.println(" No scores found, null map");
        }
    }

}

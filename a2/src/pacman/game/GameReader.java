package pacman.game;

import pacman.board.BoardItem;
import pacman.board.PacmanBoard;
import pacman.ghost.*;
import pacman.hunter.*;
import pacman.util.Direction;
import pacman.util.Position;
import pacman.util.UnpackableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReader {

    public GameReader() {}

    /**
     * Reads in a game according to the the following specification:
     * Note: any lines starting with a ';' are comments and are skipped.
     *
     * A Game File has 3 blocks ( Board, Game, Scores ) which must be in order
     * of Board first, Game second and Scores last. Each block is defined by its
     * name enclosed in square brackets. e.g: [Board], [Game], [Scores]
     *
     * There must be no empty lines before the first block and in between blocks
     * there must be a single blank line.
     *
     * [Board]
     * First line in the block is a comma separated width and height. There must
     * be (height) lines of (width) following the first line. Each character in
     * these lines ( after stripping ) must be of the BoardItem keys.
     *
     * [Game]
     * Contains newline separated list of assignments in any order which are
     * unique. An assignment is a 'Key = Value' where the Key and Value should
     * have all whitespace removed before reading. The following assignments are
     * also all mandatory otherwise the file is invalid.
     *
     * Assignment Definitions
     * Name	Value format
     * title	string
     * author	string
     * lives	Integer greater than or equal to zero
     * level	Integer greater than or equal to zero
     * score	Integer greater than or equal to zero
     * hunter	A comma separated list of attributes in the following order:
     * x, y, DIRECTION, special duration, HunterType
     * where x and y are integers, DIRECTION is the string representation of a
     * DIRECTION and the special duration is a integer greater than or equal to
     * zero.
     * blinky|inky|pinky|clyde	A comma separated list of attributes in the
     * following order:
     * x,y,DIRECTION,PHASE:PhaseDuration
     * where x and y are Integers, DIRECTION is the string representation of a
     * DIRECTION and PHASE is a string representation of the PhaseType with a
     * duration that is an Integer greater than or equal to zero
     * [Scores]
     * A newline seperated list of unique scores in the form of: 'Name : Value'
     * where name is a string and value is an Integer.
     * @param reader to read the save game from.
     * @return a PacmanGame that reflects the state from the reader.
     * @throws UnpackableException when the saved data is invalid.
     * @throws IOException when unable to read from the reader.
     */
    public static PacmanGame read(Reader reader) throws UnpackableException, IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String header, title = "", author = "";
        int lives = -1, level = -1, score = -1;
        Hunter hunter = null;
        PacmanBoard newBoard = null;
        ArrayList<Ghost> ghosts = new ArrayList<>();
        Map<String, String> infoMap;
        Map<String, Integer> scoreMap = new HashMap<>();

        while ((header = bufferedReader.readLine()) != null) {
            if (!header.startsWith(";")){
                switch (header) {
                    case "[Board]":
                        newBoard = readBoard(bufferedReader);
                        break;
                    case "[Game]":
                        infoMap = readGameAttributes(bufferedReader);
                        for (String s : infoMap.keySet()) {
                            try {
                                switch (s) {
                                    case "title":
                                        title = infoMap.get(s);
                                        break;
                                    case "author":
                                        author = infoMap.get(s);
                                        break;
                                    case "lives":
                                        lives = Integer.parseInt(infoMap.get(s));
                                        break;
                                    case "level":
                                        level = Integer.parseInt(infoMap.get(s));
                                        break;
                                    case "score":
                                        score = Integer.parseInt(infoMap.get(s));
                                        break;
                                    case "hunter":
                                        String[] hunterInfo = (infoMap.get(s)).split(",");
                                        hunter = readHunter(hunterInfo);
                                        break;
                                    default:
                                        String[] ghostInfo = (infoMap.get(s)).split(",");
                                        ghosts.add(readGhost(ghostInfo,s));
                                }
                            }catch (Exception e) {
                                throw new UnpackableException(" Error during assign");
                            }
                        }
                        if (title.equals("") || author.equals("")|| lives == -1
                                || level == -1 || score == -1 || hunter == null
                                || ghosts.size() != 4) {
                            throw new UnpackableException(" Not enough data ");
                        }
                        break;
                    case "[Scores]":
                        scoreMap = readScores(bufferedReader);
                }
            }
        }
        PacmanGame game = new PacmanGame(title, author, hunter, newBoard);
        game.setLevel(level);
        game.setLives(lives);
        game.getScores().increaseScore(score);
        game.getScores().setScores(scoreMap);
        setGhosts(ghosts, game.getGhosts());
        return game;
    }

    /*
    readBoard takes a reader as a parameter and reads the data below the [Board]
    header. It returns a PacmanBoard with the information read.
    */
    private static PacmanBoard readBoard(BufferedReader reader) throws IOException, UnpackableException{
        String[] line = reader.readLine().split(",");
        int width = Integer.parseInt(line[0]);
        int height = Integer.parseInt(line[1]);
        try {
            PacmanBoard newBoard = new PacmanBoard(width, height);
            int j = 0;
            String valueRow;
            while (!(valueRow = reader.readLine()).equals("")) {
                if (!valueRow.startsWith(";")){
                    for (int i = 0; i < valueRow.length(); i++) {
                        newBoard.setEntry(new Position(i, j),
                                BoardItem.getItem(valueRow.charAt(i)));
                    }
                    j++;
                }
            }
            return newBoard;
        } catch (IOException e) {
            throw new IOException();
        } catch (Exception e) {
            throw new UnpackableException(" Error while reading board");
        }
    }

    /*
    readGameAttributes takes a reader as a parameter and reads the data below
    the [Game] header. It returns a map containing all the assignments needed
    for the game.
    */
    private static Map<String, String> readGameAttributes(BufferedReader reader) throws IOException {
        String newAssign;
        Map<String, String> infoMap = new HashMap<>();
        while (!(newAssign = reader.readLine()).equals("")) {
            if (!newAssign.startsWith(";")) {
                String[] assign = newAssign.split("=");
                infoMap.put(assign[0].substring(0,assign[0].length() - 1),
                        assign[1].substring(1));
            }
        }
        return infoMap;
    }

    /*
    readScores takes a reader as a parameter and reads the data below
    the [Score] header. It returns a map containing all the scores of the game
    in alphabetical order.
    */
    private static Map<String, Integer> readScores(BufferedReader reader) throws IOException, UnpackableException{
        try {
            String scoreIt;
            Map<String, Integer> scoreMap = new HashMap<>();
            while ((scoreIt = reader.readLine()) != null){
                if (!scoreIt.startsWith(";")) {
                    scoreMap.put(scoreIt.split(":")[0],
                            Integer.parseInt(scoreIt.split(":")[1]));
                }
            }
            return scoreMap;
        } catch (IOException e) {
            throw new IOException();
        } catch (Exception e) {
            throw new UnpackableException(" Error while reading scores ");
        }
    }

    /*
    setGhosts takes the list of ghosts read from the file and the list of ghosts
    created in the PacmanGame as parameters assigns the respective values to
    each ghost in the PacmanGame instance.
    */
    private static void setGhosts(ArrayList<Ghost> ghosts, List<Ghost> gameGhosts) {
        int index;
        for (Ghost g: ghosts) {
            index = getGhostIndex(g.getType(), gameGhosts);
            gameGhosts.get(index).setDirection(g.getDirection());
            gameGhosts.get(index).setPosition(g.getPosition());
            gameGhosts.get(index).setPhase(g.getPhase(),
                    Integer.parseInt(g.phaseInfo().split(":")[1]));
        }
    }

    /*
    getGhostIndex takes a list of ghosts and a GhostType and returns the index
    of the ghost with that type in the list.
    */
    private static int getGhostIndex(GhostType type, List<Ghost> gameGhosts) {
        for (int i = 0; i < gameGhosts.size(); i++) {
            if (gameGhosts.get(i).getType().equals(type)){
                return i;
            }
        }
        return -1;
    }

    /*
    readHunter takes an array with the attributes needed to set up a hunter
    instance and returns a hunter those attributes.
    */
    private static Hunter readHunter(String[] hunterInfo) throws UnpackableException{
        try {
            Hunter hunter = null;
            Position hunterPosition = new Position(Integer.parseInt(hunterInfo[0]),
                    Integer.parseInt(hunterInfo[1]));
            Direction hunterDirection = readDirection(hunterInfo[2]);
            int specialDuration = Integer.parseInt(hunterInfo[3]);
            switch (hunterInfo[4]) {
                case "PHIL":
                    hunter = new Phil();
                    break;
                case "SPEEDY":
                    hunter = new Speedy();
                    break;
                case "PHASEY":
                    hunter = new Phasey();
                    break;
                case "HUNGRY":
                    hunter = new Hungry();
            }
            hunter.setPosition(hunterPosition);
            hunter.setDirection(hunterDirection);
            hunter.activateSpecial(specialDuration);
            return hunter;
        } catch (Exception e) {
            throw new UnpackableException(" Error while setting up hunter ");
        }
    }

    /*
    readGhost takes an array with the attributes needed to set up a ghost and
    the name of a type of ghost and returns an instance of the type of
    ghost with those attributes.
    */
    private static Ghost readGhost(String[] ghostInfo, String ghostName) throws UnpackableException{
        try {
            Ghost ghost = null;
            Position ghostPosition = new Position(Integer.parseInt(ghostInfo[0]),
                    Integer.parseInt(ghostInfo[1]));
            Direction ghostDirection = readDirection(ghostInfo[2]);
            String ghostPhase = ghostInfo[3].split(":")[0];
            int phaseDuration = Integer.parseInt(ghostInfo[3].split(":")[1]);
            switch (ghostName) {
                case "blinky":
                    ghost = new Blinky();
                    break;
                case "inky":
                    ghost = new Inky();
                    break;
                case "pinky":
                    ghost = new Pinky();
                    break;
                case "clyde":
                    ghost = new Clyde();
            }
            ghost.setPosition(ghostPosition);
            ghost.setDirection(ghostDirection);
            switch (ghostPhase) {
                case "CHASE":
                    ghost.setPhase(Phase.CHASE, phaseDuration);
                    break;
                case "SCATTER":
                    ghost.setPhase(Phase.SCATTER, phaseDuration);
                    break;
                case "FRIGHTENED":
                    ghost.setPhase(Phase.FRIGHTENED, phaseDuration);
            }
            return ghost;
        } catch (Exception e) {
            throw new UnpackableException(" Error while setting up ghost ");
        }
    }

    /*
    readDirection takes a string as a parameter and returns the Direction that
    the string represents.
    */
    private static Direction readDirection(String s) {
        switch (s) {
            case "UP":
                return Direction.UP;
            case "DOWN":
                return Direction.DOWN;
            case "LEFT":
                return Direction.LEFT;
            case "RIGHT":
                return Direction.RIGHT;
        }
        return null;
    }

}

package pacman.board;

import pacman.util.Position;

public class PacmanBoard {
    private int height;
    private int width;
    private BoardItem[][] board;

    public PacmanBoard(int width, int height) throws IllegalArgumentException {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        } else {
            this.height = height;
            this.width = width;
            board = new BoardItem[width][height];
            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    board[i][j] = BoardItem.NONE;
                }
            }
            for (int i = 0; i < height; i++ ) {
                board[0][i] = BoardItem.WALL;
                board[width - 1][i] = BoardItem.WALL;
            }
            for (int j = 0; j < width; j++) {
                board[j][0] = BoardItem.WALL;
                board[j][height - 1] = BoardItem.WALL;
            }
        }
    }

    public PacmanBoard(PacmanBoard other) throws NullPointerException {
        if (other==null) {
            throw new NullPointerException();
        } else {
            this.width = other.width;
            this.height = other.height;
            board = new BoardItem[other.width][];
            for(int i = 0; i < other.width; i++) {
                board[i] = other.board[i].clone();
            }
        }
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setEntry(Position position, BoardItem item) throws
            IndexOutOfBoundsException, NullPointerException {
        if (!(0 < position.getX() && position.getX() < width) ||
                !(0 < position.getY() && position.getY() < height)) {
            throw new IndexOutOfBoundsException();
        } else if (position == null || item == null) {
            throw new NullPointerException();
        } else {
            int pacmanCounter = 0;
            int ghostCounter = 0;
            if ((item.equals(BoardItem.PACMAN_SPAWN) ||
                    item.equals(BoardItem.GHOST_SPAWN)) &&
                    board[position.getX()][position.getY()].getPathable()) {

                for (int i = 0; i < getWidth(); i++) {
                    for (int j = 0; j < getHeight(); j++) {
                        if (board[i][j].equals(BoardItem.PACMAN_SPAWN) &&
                                item.equals(BoardItem.PACMAN_SPAWN)) {
                            pacmanCounter++;
                            board[i][j] = BoardItem.NONE;
                            break;
                        }else if (board[i][j].equals(BoardItem.GHOST_SPAWN) &&
                                item.equals(BoardItem.GHOST_SPAWN)){
                            ghostCounter++;
                            board[i][j] = BoardItem.NONE;
                            break;
                        }
                    }
                    if (pacmanCounter > 0 || ghostCounter > 0) {
                        break;
                    }
                }
                board[position.getX()][position.getY()] = item;
            } else if (board[position.getX()][position.getY()].getPathable()) {
                board[position.getX()][position.getY()] = item;
            } else {
                System.out.println(" Not pathable");
            }
        }
    }

    public BoardItem getEntry(Position position) throws IndexOutOfBoundsException,
            NullPointerException {
        if (position==null){
            throw new NullPointerException();
        } else if (position.getX() > getWidth() || position.getY() > getHeight()) {
            throw new IndexOutOfBoundsException();
        }
        return board[position.getX()][position.getY()];
    }

    public BoardItem eatDot(Position position) throws  IndexOutOfBoundsException,
            NullPointerException {
        if (position == null) {
            throw new NullPointerException();
        } else if (!(position.getX() > 0 && position.getX() < width) ||
                !(position.getY() > 0 && position.getY() < height)) {
            throw new IndexOutOfBoundsException();
        } else {
            BoardItem eatenItem = BoardItem.WALL;
            if (board[position.getX()][position.getY()].getPathable()) {
                if (board[position.getX()][position.getY()].equals(BoardItem.DOT)) {
                    board[position.getX()][position.getY()] = BoardItem.NONE;
                    eatenItem = BoardItem.DOT;
                } else if (board[position.getX()][position.getY()].equals(BoardItem.BIG_DOT)) {
                    board[position.getX()][position.getY()] = BoardItem.BIG_DOT_SPAWN;
                    eatenItem = BoardItem.BIG_DOT_SPAWN;
                } else {
                    eatenItem = board[position.getX()][position.getY()];
                }
            }
            return eatenItem;
        }
    }

    public Position getGhostSpawn() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j].equals(BoardItem.GHOST_SPAWN)) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }

    public Position getPacmanSpawn() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j].equals(BoardItem.PACMAN_SPAWN)) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }

    public boolean isEmpty() {
        int emptyCounter = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j].equals(BoardItem.DOT) ||
                        board[i][j].equals(BoardItem.BIG_DOT)) {
                    emptyCounter++;
                }
                if (emptyCounter > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void reset() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j].equals(BoardItem.NONE)) {
                    board[i][j] = BoardItem.DOT;
                } else if (board[i][j].equals(BoardItem.BIG_DOT_SPAWN)) {
                    board[i][j] = BoardItem.BIG_DOT;
                }
            }
        }
    }
}

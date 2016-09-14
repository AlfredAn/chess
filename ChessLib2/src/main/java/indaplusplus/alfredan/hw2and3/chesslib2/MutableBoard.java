package indaplusplus.alfredan.hw2and3.chesslib2;

public final class MutableBoard {
  
  private final Piece[][] board;
  
  public MutableBoard(int width, int height) {
    board = new Piece[width][height];
  }
  
  public MutableBoard(Board otherBoard) {
    this(otherBoard.getWidth(), otherBoard.getHeight());
    set(otherBoard);
  }
  
  public MutableBoard(MutableBoard otherBoard) {
    this(otherBoard.getWidth(), otherBoard.getHeight());
    set(otherBoard);
  }
  
  public void set(Board otherBoard) {
    if (otherBoard.getWidth() != getWidth() || otherBoard.getHeight() != getHeight()) {
      throw new IllegalArgumentException("Wrong size!");
    }
    
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        board[x][y] = otherBoard.get(x, y);
      }
    }
  }
  
  public void set(MutableBoard otherBoard) {
    if (otherBoard.getWidth() != getWidth() || otherBoard.getHeight() != getHeight()) {
      throw new IllegalArgumentException("Wrong size!");
    }
    
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        board[x][y] = otherBoard.get(x, y);
      }
    }
  }
  
  public Piece get(int x, int y) {
    return board[x][y];
  }
  
  public void set(int x, int y, Piece piece) {
    board[x][y] = piece;
  }
  
  public int getWidth() {
    return board.length;
  }
  
  public int getHeight() {
    return board[0].length;
  }
  
  /**
   * Clears the board, removing all pieces.
   */
  public void clear() {
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        set(x, y, null);
      }
    }
  }
}

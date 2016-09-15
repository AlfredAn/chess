package indaplusplus.alfredan.hw2and3.chesslib;

/**
 * A mutable version of the Board class.
 * Used to create instances of Board, and as temporary storage when processing turns.
 */
public final class MutableBoard {
  
  private final Piece[][] board;
  
  /**
   * Creates an empty MutableBoard with the specified dimensions.
   */
  public MutableBoard(int width, int height) {
    board = new Piece[width][height];
  }
  
  /**
   * Creates a MutableBoard that has the same contents as the specified Board.
   */
  public MutableBoard(Board otherBoard) {
    this(otherBoard.getWidth(), otherBoard.getHeight());
    set(otherBoard);
  }
  
  /**
   * Creates a MutableBoard that has the same contents as the specified MutableBoard.
   */
  public MutableBoard(MutableBoard otherBoard) {
    this(otherBoard.getWidth(), otherBoard.getHeight());
    set(otherBoard);
  }
  
  /**
   * Sets the contents of this MutableBoard to be the same as the specified Board.
   */
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
  
  /**
   * Sets the contents of this MutableBoard to be the same as the specified MutableBoard.
   */
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
  
  /**
   * Returns the Piece at the specified position, or null if there is none.
   */
  public Piece get(int x, int y) {
    return board[x][y];
  }
  
  /**
   * Puts the specified piece at the specified position, replacing whatever was
   * there previously.
   */
  public void set(int x, int y, Piece piece) {
    board[x][y] = piece;
  }
  
  /**
   * Returns the width of the MutableBoard.
   */
  public int getWidth() {
    return board.length;
  }
  
  /**
   * Returns the height of the MutableBoard.
   */
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

package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;

/**
 * Represents a chess board. This class is immutable.
 */
public final class Board {
  
  private final Piece[][] board;
  
  /**
   * Creates a Board with the same contents as the given MutableBoard.
   */
  public Board(MutableBoard otherBoard) {
    board = new Piece[otherBoard.getWidth()][otherBoard.getHeight()];
    
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        board[x][y] = otherBoard.get(x, y);
      }
    }
  }
  
  /**
   * Returns the piece at the specified position, or null if there is none.
   */
  public Piece get(int x, int y) {
    return board[x][y];
  }
  
  /**
   * Returns the width of the Board.
   */
  public int getWidth() {
    return board.length;
  }
  
  /**
   * Returns the height of the Board.
   */
  public int getHeight() {
    return board[0].length;
  }
  
  /**
   * Returns whether the specified position is valid (i.e. not outside the Board).
   */
  public boolean isValidPosition(int x, int y) {
    return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
  }
  
  /**
   * Returns a list of all the available moves from the piece at location (x, y).
   * @throws IllegalArgumentException If the specified location is empty.
   */
  public List<IntVector2> getAvailableMoves(int x, int y) {
    Piece piece = get(x, y);
    if (piece == null) {
      throw new IllegalArgumentException("No piece at this location!");
    } else {
      return piece.getAvailableMoves(this, x, y);
    }
  }
  
  /**
   * Makes a move and returns a new Board with the updated game state.
   * @param fromX The x coordinate of the piece to move.
   * @param fromY The y coordinate of the piece to move.
   * @param toX The x coordinate to move to.
   * @param toY The y coordinate to move to.
   * @param mutableBoard An arbitrary MutableBoard instance. This will be used
   * as temporary storage and can thus be reused later.
   * @return A new Board with an updated game state.
   */
  public Board makeMove(int fromX, int fromY, int toX, int toY, MutableBoard mutableBoard) {
    Piece piece = get(fromX, fromY);
    if (piece == null) {
      throw new IllegalArgumentException("No piece here!");
    }
    
    mutableBoard.set(this);
    
    piece.makeMove(mutableBoard, fromX, fromY, toX, toY);
    
    // call endOfTurn() on all pieces
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Piece pieceToUpdate = mutableBoard.get(x, y);
        if (pieceToUpdate != null) {
          pieceToUpdate.endOfTurn(mutableBoard, x, y, piece.team);
        }
      }
    }
    
    return new Board(mutableBoard);
  }
}

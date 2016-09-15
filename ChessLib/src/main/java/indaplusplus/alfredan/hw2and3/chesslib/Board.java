package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.pieces.King;
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
  
  /**
   * Returns whether a piece at the specified square belonging to the specified team would be vulnerable to attack.
   * Note that this doesn't support moves that can capture at a different place
   * than they move to, such as the pawn's "en passant" capture.
   */
  private boolean isDangerous(int x, int y, int team) {
    for (int xx = 0; xx < getWidth(); xx++) {
      for (int yy = 0; yy < getHeight(); yy++) {
        Piece attacker = get(xx, yy);
        
        if (attacker != null && team != attacker.team) {
          List<IntVector2> moveList = attacker.getAvailableMoves(this, xx, yy);
          
          for (int i = 0; i < moveList.size(); i++) {
            IntVector2 move = moveList.get(i);
            
            if (move.x == x && move.y == y) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  /**
   * Returns whether the specified team's king is in check.
   */
  public boolean isChecked(int team) {
    // find the king
    int kingX = -1, kingY = -1;
    
    outer: for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Piece piece = get(x, y);
        
        if (piece != null && piece.team == team && piece instanceof King) {
          kingX = x;
          kingY = y;
          break outer;
        }
      }
    }
    
    if (kingX == -1) {
      throw new IllegalStateException("Team " + team + " has no king.");
    }
    
    return isDangerous(kingX, kingY, team);
  }
}

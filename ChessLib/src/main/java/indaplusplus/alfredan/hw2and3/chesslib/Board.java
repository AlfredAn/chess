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
    return getAvailableMoves(x, y, true);
  }
  
  /**
   * Returns a list of all the available moves from the piece at location (x, y).
   * @param x The x position of the piece.
   * @param y The y position of the piece.
   * @param testIfKingChecked Whether to remove moves that would result in the king being checked.
   * @param team The team whose king to check.
   * @throws IllegalArgumentException If the specified location is empty.
   */
  private List<IntVector2> getAvailableMoves(int x, int y, boolean testIfKingChecked) {
    Piece piece = get(x, y);
    if (piece == null) {
      throw new IllegalArgumentException("No piece at this location!");
    }
    
    List<IntVector2> moveList = piece.getAvailableMoves(this, x, y);
    
    if (testIfKingChecked) {
      // find the king
      int kingX = -1, kingY = -1;
      
      if (piece instanceof King) {
        kingX = x;
        kingY = y;
      } else {
        outer: for (int xx = 0; xx < getWidth(); xx++) {
          for (int yy = 0; yy < getHeight(); yy++) {
            Piece isItKing = get(xx, yy);
            
            if (isItKing != null && isItKing.team == piece.team && isItKing instanceof King) {
              kingX = xx;
              kingY = yy;
              break outer;
            }
          }
        }
      }
      
      if (kingX == -1) {
        // no king, just return
        // this is often the case in tests
        return moveList;
      }
      
      // remove all invalid moves from the list
      for (int i = 0; i < moveList.size(); i++) {
        IntVector2 move = moveList.get(i);
        
        // temporarily make the move to see if it would lead to a check
        Board tempBoard = makeMoveNoCheck(x, y, move.x, move.y);
        if ((piece instanceof King && tempBoard.isDangerous(move.x, move.y, piece.team, false))
                || ((!(piece instanceof King)) && tempBoard.isDangerous(kingX, kingY, piece.team, false))) {
          moveList.remove(i);
          i--;
        }
      }
    }
    
    return moveList;
  }
  
  /**
   * Makes a move and returns a new Board with the updated game state.
   * @param fromX The x coordinate of the piece to move.
   * @param fromY The y coordinate of the piece to move.
   * @param toX The x coordinate to move to.
   * @param toY The y coordinate to move to.
   * @return A new Board with an updated game state.
   * @throws IllegalArgumentException If the piece doesn't exist or if the move is invalid.
   */
  public Board makeMove(int fromX, int fromY, int toX, int toY) {
    Piece piece = get(fromX, fromY);
    if (piece == null) {
      throw new IllegalArgumentException("No piece here!");
    }
    
    if (isValidMove(fromX, fromY, toX, toY)) {
      return makeMoveNoCheck(fromX, fromY, toX, toY);
    } else {
      throw new IllegalArgumentException("Illegal move!");
    }
  }
  
  /**
   * Makes a move and returns a new Board with the updated game state.
   * This method doesn't check if the move is valid before making it.
   * @param fromX The x coordinate of the piece to move.
   * @param fromY The y coordinate of the piece to move.
   * @param toX The x coordinate to move to.
   * @param toY The y coordinate to move to.
   * @param mutableBoard An arbitrary MutableBoard instance. This will be used
   * as temporary storage and can thus be reused later.
   * @return A new Board with an updated game state.
   * @throws IllegalArgumentException If the piece doesn't exist.
   */
  private Board makeMoveNoCheck(int fromX, int fromY, int toX, int toY) {
    Piece piece = get(fromX, fromY);
    if (piece == null) {
      throw new IllegalArgumentException("No piece here!");
    }
    
    MutableBoard mutableBoard = new MutableBoard(this);
    
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
   * Returns whether the piece on the specified position can move to the specified square.
   */
  public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
    List<IntVector2> moveList = getAvailableMoves(fromX, fromY);
    
    for (int i = 0; i < moveList.size(); i++) {
      IntVector2 validMove = moveList.get(i);
      
      if (validMove.x == toX && validMove.y == toY) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns whether the piece on the specified position can move to the specified square.
   */
  public final boolean isValidMove(int fromX, int fromY, IntVector2 destination) {
    return isValidMove(fromX, fromY, destination.x, destination.y);
  }
  
  /**
   * Returns whether a piece at the specified square belonging to the specified team would be vulnerable to attack.
   * Note that this doesn't support moves that can capture at a different place
   * than they move to, such as the pawn's "en passant" capture.
   */
  public boolean isDangerous(int x, int y, int team, boolean dontRecurse) {
    if (dontRecurse) {
      return false;
    }
    
    for (int xx = 0; xx < getWidth(); xx++) {
      for (int yy = 0; yy < getHeight(); yy++) {
        Piece attacker = get(xx, yy);
        
        if (attacker != null && team != attacker.team) {
          
          List<IntVector2> moveList = getAvailableMoves(xx, yy, false);
          
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
      // no king, just return false
      // this is often the case in tests
      return false;
    }
    
    return isDangerous(kingX, kingY, team, false);
  }
  
  /**
   * Returns whether the specified team can make any moves at all.
   * If this method returns false for the team whose turn it currently is,
   * the game has ended, either in a checkmate or a draw. You can check which
   * it is using the isChecked() method.
   */
  public boolean canMove(int team) {
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Piece piece = get(x, y);
        
        if (piece != null && piece.team == team) {
          List<IntVector2> moveList = getAvailableMoves(x, y);
          if (moveList.size() > 0) {
            return true;
          }
        }
      }
    }
    return false;
  }
}

package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;

/**
 * Represents a chess piece.
 * Can be placed and moved on a ChessBoard.
 */
public abstract class ChessPiece {

  /**
   * The ChessBoard that this piece belongs to.
   * Set by the ChessBoard when the piece is added or moved.
   * May be null if the piece is not currently on a board.
   */
  ChessBoard board;
  
  /**
   * The x position of this piece on the board.
   * Set by the ChessBoard when the piece is added or moved.
   * Undefined if the piece is not currently on a board.
   */
  int xPos;
  
  /**
   * The y position of this piece on the board.
   * Set by the ChessBoard when the piece is added or moved.
   * Undefined if the piece is not currently on a board.
   */
  int yPos;
  
  /**
   * The team that this piece belongs to.
   * Normally this can only be either Team.BLACK or Team.WHITE, but any number is valid.
   */
  private int team;
  
  public ChessPiece(int team) {
    this.team = team;
  }
  
  public final int getX() {
    return xPos;
  }
  
  public final int getY() {
    return yPos;
  }
  
  public final void setTeam(int team) {
    this.team = team;
  }
  
  public final int getTeam() {
    return team;
  }
  
  /**
   * Returns a list of all the possible squares that this piece can move to.
   */
  public abstract List<IntVector2> getAvailableMoves();
  
  /**
   * Returns whether this piece can move to the specified square.
   */
  public boolean isValidMove(int x, int y) {
    checkInitialized();
    for (IntVector2 validMove : getAvailableMoves()) {
      if (validMove.x == x && validMove.y == y) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns whether this piece can move to the specified square.
   */
  public final boolean isValidMove(IntVector2 destination) {
    return isValidMove(destination.x, destination.y);
  }
  
  /**
   * Returns the ChessBoard that this piece belongs to,
   * or null if it hasn't been added to one yet.
   */
  public final ChessBoard getBoard() {
    return board;
  }
  
  /**
   * Throws an IllegalStateException if this piece has not been added to a board.
   */
  protected final void checkInitialized() {
    if (board == null) {
      throw new IllegalStateException("The piece must first be added to a board!");
    }
  }
}

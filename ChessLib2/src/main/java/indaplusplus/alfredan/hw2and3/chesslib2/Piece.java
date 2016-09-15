package indaplusplus.alfredan.hw2and3.chesslib2;

import indaplusplus.alfredan.hw2and3.chesslib2.util.IntVector2;
import java.util.List;

public abstract class Piece {
  
  public final int team;
  
  public Piece(int team) {
    this.team = team;
  }
  
  /**
   * Returns a list of all the possible squares that this piece can move to.
   * This method must be overridden by all subclasses to define the piece's behavior.
   * getAvailableMoves() will convert the returned list to a MoveSet and cache it for the duration of the turn.
   */
  protected abstract List<IntVector2> getAvailableMoves(Board board, int xPos, int yPos);
  
  /**
   * Returns whether this piece can move to the specified square.
   */
  public boolean isValidMove(Board board, int xPos, int yPos, int moveX, int moveY) {
    for (IntVector2 validMove : getAvailableMoves(board, xPos, yPos)) {
      if (validMove.x == moveX && validMove.y == moveY) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Returns whether this piece can move to the specified square.
   */
  public final boolean isValidMove(Board board, int xPos, int yPos, IntVector2 destination) {
    return isValidMove(board, xPos, yPos, destination.x, destination.y);
  }
  
  protected void makeMove(MutableBoard board, int xPos, int yPos, int moveX, int moveY) {
    board.set(xPos, yPos, null);
    board.set(moveX, moveY, this);
  }
  
  /**
   * Called immediately after a turn is made.
   * @param board The board that is currently being processed.
   * @param xPos The x position of this piece.
   * @param yPos The y position of this piece.
   * @param teamThatMoved The team that just made a move.
   */
  protected void endOfTurn(MutableBoard board, int xPos, int yPos, int teamThatMoved) {}
}

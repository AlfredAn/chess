package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;

/**
 * Defines a chess piece. This class and all subclasses must be immutable.
 * <p>The only restriction on what you can do is that no piece can be able to
 * capture the king without directly moving to him, as that isn't supported
 * right now.
 */
public abstract class Piece {
  
  public final int team;
  
  /**
   * Creates a new Piece with the specified team.
   */
  public Piece(int team) {
    this.team = team;
  }
  
  /**
   * Returns a list of all the possible squares that this piece can move to.
   * This method must be overridden by all subclasses to define the piece's behavior.
   */
  protected abstract List<IntVector2> getAvailableMoves(Board board, int xPos, int yPos);
  
  /**
   * Makes a move. Intended to be overridden by subclasses for custom behavior.
   * @param board The board that is currently being processed.
   * @param xPos The x position of this piece.
   * @param yPos The y position of this piece.
   * @param moveX The x position that the piece is supposed to move to.
   * Guaranteed to be within bounds.
   * @param moveY The y position that the piece is supposed to move to.
   * Guaranteed to be within bounds.
   */
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

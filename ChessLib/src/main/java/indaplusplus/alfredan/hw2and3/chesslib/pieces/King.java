package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;

/**
 * A king from standard chess.
 */
public final class King extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}, // straights
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}  // diagonals
  });
  
  /**
   * Whether this king has ever moved. Used to determine whether or not you can castle.
   */
  public final boolean hasMoved;
  
  public King(int team) {
    this(team, false);
  }
  
  public King(int team, boolean hasMoved) {
    super(team);
    
    this.hasMoved = hasMoved;
  }
  
  @Override
  protected MoveSet getSingleMoveSet() {
    return MOVESET;
  }
  
  @Override
  protected void makeMove(MutableBoard board, int xPos, int yPos, int moveX, int moveY) {
    King newState;
    
    if (hasMoved) {
      newState = this;
    } else {
      newState = new King(team, true);
    }
    
    board.set(moveX, moveY, newState);
  }
}
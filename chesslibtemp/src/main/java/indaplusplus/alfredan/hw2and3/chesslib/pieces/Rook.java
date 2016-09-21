package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;

/**
 * A rook from standard chess.
 */
public final class Rook extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}
  });
  
  /**
   * Whether this rook has ever moved. Used to determine whether or not you can castle.
   */
  public final boolean hasMoved;
  
  public Rook(int team) {
    this(team, false);
  }
  
  public Rook(int team, boolean hasMoved) {
    super(team);
    
    this.hasMoved = hasMoved;
  }
  
  @Override
  protected MoveSet getRepeatableMoveSet() {
    return MOVESET;
  }
  
  @Override
  protected void makeMove(MutableBoard board, int xPos, int yPos, int moveX, int moveY) {
    Rook newState;
    
    if (hasMoved) {
      newState = this;
    } else {
      newState = new Rook(team, true);
    }
    
    board.set(moveX, moveY, newState);
    board.set(xPos, yPos, null);
  }
}

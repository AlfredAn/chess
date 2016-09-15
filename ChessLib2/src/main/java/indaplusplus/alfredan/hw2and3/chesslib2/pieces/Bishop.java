package indaplusplus.alfredan.hw2and3.chesslib2.pieces;

import indaplusplus.alfredan.hw2and3.chesslib2.util.MoveSet;

/**
 * A bishop from standard chess.
 */
public final class Bishop extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}
  });
  
  public Bishop(int team) {
    super(team);
  }
  
  @Override
  protected MoveSet getRepeatableMoveSet() {
    return MOVESET;
  }
}
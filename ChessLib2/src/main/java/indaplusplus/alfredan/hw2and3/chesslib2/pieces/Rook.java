package indaplusplus.alfredan.hw2and3.chesslib2.pieces;

import indaplusplus.alfredan.hw2and3.chesslib2.util.MoveSet;

/**
 * A rook from standard chess.
 */
public final class Rook extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}
  });
  
  public Rook(int team) {
    super(team);
  }
  
  @Override
  protected MoveSet getRepeatableMoveSet() {
    return MOVESET;
  }
}

package indaplusplus.alfredan.hw2and3.chesslib2.pieces;

import indaplusplus.alfredan.hw2and3.chesslib2.util.MoveSet;

/**
 * A knight from standard chess.
 */
public final class Knight extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  2}, { 2,  1},
    { 2, -1}, { 1, -2},
    {-1, -2}, {-2, -1},
    {-2,  1}, {-1,  2}});
  
  public Knight(int team) {
    super(team);
  }
  
  @Override
  protected MoveSet getSingleMoveSet() {
    return MOVESET;
  }
}
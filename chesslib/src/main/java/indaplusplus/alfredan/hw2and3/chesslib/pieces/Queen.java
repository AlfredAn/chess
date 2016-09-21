package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;

/**
 * A queen from standard chess.
 */
public final class Queen extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}, // straights
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}  // diagonals
  });
  
  public Queen(int team) {
    super(team);
  }
  
  @Override
  protected MoveSet getRepeatableMoveSet() {
    return MOVESET;
  }
}

package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;

/**
 * A king from standard chess.
 */
public final class King extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}, // straights
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}  // diagonals
  });
  
  public King(int team) {
    super(team);
  }
  
  @Override
  protected MoveSet getSingleMoveSet() {
    return MOVESET;
  }
}
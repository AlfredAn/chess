package indaplusplus.alfredan.hw2and3.chesslib.pieces;

/**
 * A king from standard chess.
 */
public class King extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}, // straights
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}  // diagonals
  });
  
  public King(int team) {
    super(team, MOVESET, null);
  }
  
  /**
   * Returns whether this king is currently in check.
   * Convenience method that simply calls the corresponding method on the ChessBoard.
   * NOTE: If you have custom pieces that can capture the king without directly moving to it
   * (like how pawns can do an "en passant" capture), then this method won't work.
   */
  public boolean isInCheck() {
    return getBoard().isInCheck(this);
  }
}

package indaplusplus.alfredan.hw2and3.chesslib.pieces;

/**
 * A bishop from standard chess.
 */
public class Bishop extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}
  });
  
  public Bishop(int team) {
    super(team, null, MOVESET);
  }
}

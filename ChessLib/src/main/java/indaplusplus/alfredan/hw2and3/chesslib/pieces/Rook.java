package indaplusplus.alfredan.hw2and3.chesslib.pieces;

/**
 * A rook from standard chess.
 */
public class Rook extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}
  });
  
  public Rook(int team) {
    super(team, null, MOVESET);
  }
}

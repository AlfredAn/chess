package indaplusplus.alfredan.hw2and3.chesslib.pieces;

/**
 * A knight from standard chess.
 */
public class Knight extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  2}, { 2,  1},
    { 2, -1}, { 1, -2},
    {-1, -2}, {-2, -1},
    {-2,  1}, {-1,  2}});
  
  public Knight(int team) {
    super(team, MOVESET, null);
  }
}

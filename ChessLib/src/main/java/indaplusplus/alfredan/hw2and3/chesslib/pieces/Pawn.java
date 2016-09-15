package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;

public final class Pawn extends TemplatePiece {
  
  /**
   * Constants for the different types of moves the pawn can make.
   */
  private static final int
          M_NORMAL = 0,
          M_DOUBLE = 1,
          M_CAPTURE_LEFT = 2,
          M_CAPTURE_RIGHT = 3;
  
  private static final MoveSet MOVESET_BLACK = new MoveSet(new int[][] {
    // 0: normal move
    { 0, -1},
    
    // 1: optional first move
    { 0, -2},
    
    // 2 and 3: diagonal captures
    {-1, -1},
    { 1, -1}
  });
  
  private static final MoveSet MOVESET_WHITE = new MoveSet(new int[][] {
    // 0: normal move
    { 0,  1},
    
    // 1: optional first move
    { 0,  2},
    
    // 2 and 3: diagonal captures
    {-1,  1},
    { 1,  1}
  });
  
  private static MoveSet getMoveSetForTeam(int team) {
    switch (team) {
      case Team.BLACK:
        return MOVESET_BLACK;
      case Team.WHITE:
        return MOVESET_WHITE;
      default:
        // this class only supports two-team games
        return MoveSet.EMPTY;
    }
  }
  
  /**
   * Whether this pawn has ever moved. Used to determine whether a double move
   * can be made.
   */
  private final boolean hasMoved;
  
  /**
   * Greater than zero if the piece has just made a double move and is thus vulnerable to an
   * en passant capture. Will be decremented every turn until it hits zero.
   * This is set to 2 when a double move is made, but is immediately decremented
   * to 1 in the endOfTurn() method. It will thus only remain positive for one turn.
   */
  private final int enPassantVulnerable;
  
  public Pawn(int team) {
    this(team, false, 0);
  }
  
  public Pawn(int team, boolean hasMoved, int enPassantVulnerable) {
    super(team);
    
    this.hasMoved = hasMoved;
    this.enPassantVulnerable = enPassantVulnerable;
  }
  
  private int getForwardYDirection() {
    return team == Team.WHITE ? 1 : -1;
  }

  @Override
  protected MoveSet getSingleMoveSet() {
    return getMoveSetForTeam(team);
  }
  
  @Override
  protected boolean canMoveSingle(Board board, int xPos, int yPos, IntVector2 delta, int moveX, int moveY, int index) {
    Piece pieceAtDestination = board.get(moveX, moveY);
    
    switch (index) {
      case M_NORMAL:
        return pieceAtDestination == null;
      case M_DOUBLE:
        return pieceAtDestination == null && !hasMoved;
      case M_CAPTURE_LEFT:
      case M_CAPTURE_RIGHT:
        // this location has to be valid because y == getYPos() and x == moveX
        Piece pieceAtEnPassantLocation = board.get(moveX, moveY - getForwardYDirection());
        if (pieceAtEnPassantLocation instanceof Pawn) {
          Pawn enemyPawn = (Pawn)pieceAtEnPassantLocation;
          if (enemyPawn.enPassantVulnerable > 0) {
            System.out.println("en passant");
            return true;
          }
        }
        return pieceAtDestination != null && pieceAtDestination.team != team;
      default: // cannot happen
        throw new AssertionError();
    }
  }
  
  @Override
  protected void makeMove(MutableBoard board, int xPos, int yPos, int moveX, int moveY) {
    int dx = moveX - xPos;
    int dy = moveY - yPos;
    boolean isDoubleMove = Math.abs(dy) == 2;
    boolean isCapture = dx * dy != 0; // check if the move is diagonal
    
    Piece pieceAtDestination = board.get(moveX, moveY);
    if (isCapture && pieceAtDestination == null) {
      // must be an en passant capture
      board.set(moveX, moveY - getForwardYDirection(), null);
    }
    
    boolean newHasMoved = true;
    
    // set this to 2, as it will immediately be decremented to 1 when endOfTurn() is called
    int newEnPassantVulnerable = isDoubleMove ? 2 : enPassantVulnerable;
    
    // reuse this instance if the internal state hasn't changed
    Pawn newState;
    if (newHasMoved == hasMoved && newEnPassantVulnerable == enPassantVulnerable) {
      newState = this;
    } else {
      newState = new Pawn(team, newHasMoved, newEnPassantVulnerable);
    }
    
    board.set(moveX, moveY, newState);
    board.set(xPos, yPos, null);
  }
  
  @Override
  protected void endOfTurn(MutableBoard board, int xPos, int yPos, int teamThatMoved) {
    if (enPassantVulnerable > 0) {
      board.set(xPos, yPos, new Pawn(team, hasMoved, enPassantVulnerable - 1));
    }
  }
}

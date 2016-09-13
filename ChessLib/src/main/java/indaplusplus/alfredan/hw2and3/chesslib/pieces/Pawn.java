package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.BoardEvent;
import indaplusplus.alfredan.hw2and3.chesslib.ChessPiece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;

/**
 * A pawn from standard chess.
 */
public class Pawn extends TemplatePiece {
  
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
  
  private int getForwardYDirection() {
    return team == Team.WHITE ? 1 : -1;
  }
  
  /**
   * Keeps track of whether the pawn has moved.
   * Used to check if the pawn can make a double move.
   */
  private boolean hasMoved = false;
  
  /**
   * Whether the pawn has just made a double move and is vulnerable to en passant.
   * This gets reset to false every turn.
   */
  private boolean enPassantVulnerable = false;
  
  /**
   * Create a pawn with the specified team.
   */
  public Pawn(int team) {
    super(team, getMoveSetForTeam(team), null);
  }
  
  @Override
  protected boolean canMoveSingle(IntVector2 delta, int moveX, int moveY, int index) {
    ChessPiece pieceAtDestination = getBoard().getPiece(moveX, moveY);
    
    switch (index) {
      case M_NORMAL:
        return pieceAtDestination == null;
      case M_DOUBLE:
        return pieceAtDestination == null && !hasMoved;
      case M_CAPTURE_LEFT:
      case M_CAPTURE_RIGHT:
        // this location has to be valid because y == getYPos() and x == moveX
        ChessPiece pieceAtEnPassantLocation = getBoard().getPiece(moveX, moveY - getForwardYDirection());
        if (pieceAtEnPassantLocation instanceof Pawn) {
          Pawn enemyPawn = (Pawn)pieceAtEnPassantLocation;
          if (enemyPawn.enPassantVulnerable) {
            return true;
          }
        }
        
        return pieceAtDestination != null && pieceAtDestination.team != team;
      default: // cannot happen
        throw new AssertionError();
    }
  }
  
  @Override
  protected void performMove(int x, int y) {
    hasMoved = true;
    
    int dx = x - getX();
    int dy = y - getY();
    boolean isDoubleMove = Math.abs(dy) == 2;
    boolean isCapture = dx * dy != 0; // check if the move is diagonal
    
    ChessPiece pieceAtDestination = getBoard().getPiece(x, y);
    if (isCapture && pieceAtDestination == null) {
      // must be an en passant capture
      ChessPiece pieceToCapture = getBoard().getPiece(x, y - getForwardYDirection());
      getBoard().removePiece(pieceToCapture);
    }
    
    super.performMove(x, y);
    
    // this has to be placed after super.performMove() because it will otherwise be reset to false
    if (isDoubleMove) {
      enPassantVulnerable = true;
    }
  }
  
  @Override
  protected void boardEvent(BoardEvent event) {
    if (event instanceof BoardEvent.EndOfTurnEvent) {
      enPassantVulnerable = false;
    }
  }
}

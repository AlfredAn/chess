package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;
import java.util.List;

/**
 * A king from standard chess.
 */
public final class King extends TemplatePiece {
  
  private static final MoveSet MOVESET = new MoveSet(new int[][] {
    { 1,  0}, {-1,  0}, { 0,  1}, { 0, -1}, // straights
    { 1,  1}, {-1,  1}, {-1, -1}, { 1, -1}  // diagonals
  });
  
  /**
   * Whether this king has ever moved. Used to determine whether or not you can castle.
   */
  public final boolean hasMoved;
  
  public King(int team) {
    this(team, false);
  }
  
  public King(int team, boolean hasMoved) {
    super(team);
    
    this.hasMoved = hasMoved;
  }
  
  @Override
  protected MoveSet getSingleMoveSet() {
    return MOVESET;
  }
  
  @Override
  protected void addCustomMoves(Board board, int xPos, int yPos, List<IntVector2> moveList) {
    /* --Castling--
     * Four conditions (from Wikipedia):
     * 1. The king and rook involved in castling must not have previously moved.
     * 2. There must be no pieces between the king and the rook.
     * 3. The king may not currently be in check, nor may the king pass through
     *    or end up in a square that is under attack by an enemy piece
     *    (though the rook is permitted to be under attack and to pass over an attacked square).
     * 4. The king and the rook must be on the same rank (y position).
     */
    
    if (!hasMoved && !board.isDangerous(xPos, yPos, team, true)) {
      // to the right
      if (xPos < board.getWidth() - 3
              && board.get(xPos + 1, yPos) == null
              && board.get(xPos + 2, yPos) == null
              && !board.isDangerous(xPos + 1, yPos, team, true)
              && !board.isDangerous(xPos + 2, yPos, team, true)) {
        // check if there is an eligible rook
        for (int x = xPos + 3; x < board.getWidth(); x++) {
          Piece piece = board.get(x, yPos);
          if (piece instanceof Rook && piece.team == team) {
            Rook rook = (Rook)piece;
            if (!rook.hasMoved) {
              // all conditions met
              moveList.add(new IntVector2(xPos + 2, yPos));
              break;
            } else {
              // the rook has already moved and is thus not eligible
              break;
            }
          } else if (piece != null) {
            // encountered a piece other than a rook
            break;
          }
        }
      }
      
      // to the left
      if (xPos >= 3
              && board.get(xPos - 1, yPos) == null
              && board.get(xPos - 2, yPos) == null
              && !board.isDangerous(xPos - 1, yPos, team, true)
              && !board.isDangerous(xPos - 2, yPos, team, true)) {
        // check if there is an eligible rook
        for (int x = xPos - 3; x >= 0; x--) {
          Piece piece = board.get(x, yPos);
          if (piece instanceof Rook && piece.team == team) {
            Rook rook = (Rook)piece;
            if (!rook.hasMoved) {
              // all conditions met
              moveList.add(new IntVector2(xPos - 2, yPos));
              break;
            } else {
              // the rook has already moved and is thus not eligible
              break;
            }
          } else if (piece != null) {
            // encountered a piece other than a rook
            break;
          }
        }
      }
    }
  }
  
  @Override
  protected void makeMove(MutableBoard board, int xPos, int yPos, int moveX, int moveY) {
    King newState;
    
    if (hasMoved) {
      newState = this;
    } else {
      newState = new King(team, true);
    }
    
    int rookX = -1, castlingDir = 0;
    if (moveX - xPos == 2) {
      // castling to the right
      castlingDir = 1;
      
      // find the rook
      for (int x = moveX + 1; x < board.getWidth(); x++) {
        Piece piece = board.get(x, yPos);
        if (piece instanceof Rook) {
          // found it!
          rookX = x;
          break;
        } else if (piece != null) {
          break;
        }
      }
      if (rookX == -1) {
        // this cannot happen unless something went wrong before
        throw new AssertionError("Error in King.addCustomMoves()");
      }
    } else if (moveX - xPos == -2) {
      // castling to the left
      castlingDir = -1;
      
      // find the rook
      for (int x = moveX - 1; x >= 0; x--) {
        Piece piece = board.get(x, yPos);
        System.out.println("x: " + x + ", piece: " + piece);
        if (piece instanceof Rook) {
          // found it!
          rookX = x;
          break;
        } else if (piece != null) {
          break;
        }
      }
      if (rookX == -1) {
        // this cannot happen unless something went wrong before
        throw new AssertionError("Error in King.addCustomMoves()");
      }
    }
    
    if (castlingDir != 0) {
      // move the rook to our side, depending on the castling direction
      board.set(moveX - castlingDir, moveY, new Rook(team, true));
      board.set(rookX, moveY, null);
    }
    
    board.set(moveX, moveY, newState);
    board.set(xPos, yPos, null);
  }
}
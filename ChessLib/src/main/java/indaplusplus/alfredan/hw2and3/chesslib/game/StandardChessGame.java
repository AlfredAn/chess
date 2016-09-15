package indaplusplus.alfredan.hw2and3.chesslib.game;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Bishop;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.King;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Knight;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Pawn;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Rook;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;

public final class StandardChessGame {
  
  private static final String[] standardBoardStr = new String[] {
    "RNBQKBNR",
    "PPPPPPPP",
    "        ",
    "        ",
    "        ",
    "        ",
    "PPPPPPPP",
    "RNBQKBNR"
  };
  
  static {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 8; x++) {
        int team = y < 2 ? Team.WHITE : Team.BLACK;
        
        switch (standardBoardStr[y].charAt(x)) {
          case 'K':
            mBoard.set(x, y, new King(team));
            break;
          case 'Q':
            mBoard.set(x, y, new Queen(team));
            break;
          case 'R':
            mBoard.set(x, y, new Rook(team));
            break;
          case 'B':
            mBoard.set(x, y, new Bishop(team));
            break;
          case 'N':
            mBoard.set(x, y, new Knight(team));
            break;
          case 'P':
            mBoard.set(x, y, new Pawn(team));
            break;
        }
      }
    }
    
    STARTING_BOARD = new Board(mBoard);
  }
  
  public static final Board STARTING_BOARD;
  
  private Board board;
  
  private int turn = Team.WHITE;
  
  private IntVector2 pendingPawnPromotion;
  
  public StandardChessGame() {
    this(STARTING_BOARD);
  }
  
  public StandardChessGame(Board startingBoard) {
    board = startingBoard;
  }
  
  /**
   * Try to move the piece at (fromX, fromY) to position (toX, toY).
   * @return Whether the move was successful.
   */
  public boolean move(int fromX, int fromY, int toX, int toY) {
    if (canPromotePawn()) {
      return false;
    }
    
    Piece piece = board.get(fromX, fromY);
    
    if (piece != null && piece.team == turn && board.isValidMove(fromX, fromY, toX, toY)) {
      board = board.makeMove(fromX, fromY, toX, toY);
      
      if (piece instanceof Pawn
              && ((piece.team == Team.WHITE && toY == 7)
               || (piece.team == Team.BLACK && toY == 0))) {
        pendingPawnPromotion = new IntVector2(toX, toY);
        return true;
      }
      turn = 1 - turn;
      return true;
    } else {
      return false;
    }
  }
  
  public boolean canPromotePawn() {
    return pendingPawnPromotion != null;
  }
  
  public void promotePawn(Class<? extends Piece> pieceType) {
    Piece promoted;
    
    if (pieceType.equals(Queen.class)) {
      promoted = new Queen(turn);
    } else if (pieceType.equals(Rook.class)) {
      promoted = new Rook(turn);
    } else if (pieceType.equals(Bishop.class)) {
      promoted = new Bishop(turn);
    } else if (pieceType.equals(Knight.class)) {
      promoted = new Knight(turn);
    } else {
      throw new IllegalArgumentException();
    }
    
    MutableBoard mBoard = new MutableBoard(board);
    
    mBoard.set(pendingPawnPromotion.x, pendingPawnPromotion.y, promoted);
    
    board = new Board(mBoard);
    
    turn = 1 - turn;
  }
  
  /**
   * Returns the current state of the chess board.
   */
  public Board getBoard() {
    return board;
  }
  
  /**
   * Returns whose turn it is.
   * Possible values are Team.BLACK and Team.WHITE.
   */
  public int getTurn() {
    return turn;
  }
}

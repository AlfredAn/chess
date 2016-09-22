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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class StandardChessGame {
  
  public enum GameStatus {
    NORMAL,
    BLACK_WIN,
    WHITE_WIN,
    DRAW
  }
  
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
  
  private int turn;
  
  private IntVector2 pendingPawnPromotion;
  
  private GameStatus gameStatus = GameStatus.NORMAL;
  
  private final Map<BoardState, BoardStateCount> seenStates = new HashMap<>();
  
  private int lastTurnPieceCount;
  private int turnsSinceCaptureOrPawnMove;
  
  private int turnCounter;
  
  public StandardChessGame() {
    this(STARTING_BOARD);
  }
  
  public StandardChessGame(Board startingBoard) {
    board = startingBoard;
    turnFinished();
    
    turn = Team.WHITE;
    turnsSinceCaptureOrPawnMove = 0;
    turnCounter = 0;
  }
  
  /**
   * Try to move the piece at (fromX, fromY) to position (toX, toY).
   * @return Whether the move was successful.
   */
  public boolean move(int fromX, int fromY, int toX, int toY) {
    if (canPromotePawn() || gameStatus != GameStatus.NORMAL) {
      return false;
    }
    
    Piece piece = board.get(fromX, fromY);
    
    if (piece != null && piece.team == turn && board.isValidMove(fromX, fromY, toX, toY)) {
      board = board.makeMove(fromX, fromY, toX, toY);
      
      if (piece instanceof Pawn) {
        // -1 since it will immediately be incremented
        turnsSinceCaptureOrPawnMove = -1;
      }
      
      if (piece instanceof Pawn
              && ((piece.team == Team.WHITE && toY == 7)
               || (piece.team == Team.BLACK && toY == 0))) {
        pendingPawnPromotion = new IntVector2(toX, toY);
        return true;
      }
      turnFinished();
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Causes the player whose turn it currently is to resign.
   */
  public void resign() {
    if (gameStatus != GameStatus.NORMAL) {
      return;
    }
    gameStatus = turn == Team.WHITE ? GameStatus.BLACK_WIN : GameStatus.WHITE_WIN;
  }
  
  public void resignOther() {
    if (gameStatus != GameStatus.NORMAL) {
      return;
    }
    gameStatus = turn == Team.WHITE ? GameStatus.WHITE_WIN : GameStatus.BLACK_WIN;
  }
  
  public void declareDraw() {
    if (gameStatus != GameStatus.NORMAL) {
      return;
    }
    
    gameStatus = GameStatus.DRAW;
  }
  
  private void turnFinished() {
    turn = 1 - turn;
    
    turnCounter++;
    
    // check if the board state has been seen before
    BoardState newState = new BoardState(board, turn);
    
    BoardStateCount counter = seenStates.get(newState);
    if (counter == null) {
      counter = new BoardStateCount(newState);
      seenStates.put(newState, counter);
    }
    counter.count++;
    
    // fifty move rule
    turnsSinceCaptureOrPawnMove++;
    
    int pieceCount = board.countPieces();
    if (pieceCount < lastTurnPieceCount) {
      turnsSinceCaptureOrPawnMove = 0;
    }
    
    // one move per standard chess definition is two turns on this counter
    if (turnsSinceCaptureOrPawnMove >= 100) {
      gameStatus = GameStatus.DRAW;
      return;
    }
    
    // threefold repetition rule, draw the game if the same position is reached 3 times
    if (counter.count >= 3) {
      gameStatus = GameStatus.DRAW;
      return;
    }
    
    boolean gameOver = !board.canMove(turn);
    
    if (gameOver) {
      // is it checkmate?
      boolean checkmate = board.isChecked(turn);
      
      if (checkmate) {
        gameStatus = turn == Team.BLACK ? GameStatus.WHITE_WIN : GameStatus.BLACK_WIN;
      } else {
        gameStatus = GameStatus.DRAW;
      }
    } else {
      gameStatus = GameStatus.NORMAL;
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
    
    pendingPawnPromotion = null;
    
    turnFinished();
  }
  
  /**
   * Returns the game status. It is:
   * NORMAL if the game is still going,
   * BLACK_WIN if black has won,
   * WHITE_WIN if white has won, or
   * DRAW if the game has ended in a draw.
   */
  public GameStatus getGameStatus() {
    return gameStatus;
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
  
  /**
   * Returns the total number of moves that have been made.
   * A move is defined as white making a move, followed by black.
   * @return 
   */
  public int getMoveCounter() {
    return turnCounter / 2;
  }
  
  private static final class BoardStateCount {
    private final BoardState state;
    private int count = 0;
    
    private BoardStateCount(BoardState state) {
      this.state = state;
    }
  }
  
  private static final class BoardState {
    private final Board board;
    private final int turn;
    
    private BoardState(Board board, int turn) {
      this.board = board;
      this.turn = turn;
    }
    
    @Override
    public boolean equals(Object o) {
      if (o == this) return true;
      if (!(o instanceof BoardState)) return false;
      
      BoardState other = (BoardState)o;
      
      return other.turn == turn && Objects.equals(other.board, board);
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 53 * hash + turn;
      hash = 53 * hash + Objects.hashCode(board);
      return hash;
    }
  }
}

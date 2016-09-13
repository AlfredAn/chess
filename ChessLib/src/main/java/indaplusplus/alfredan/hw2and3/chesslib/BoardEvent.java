package indaplusplus.alfredan.hw2and3.chesslib;

/**
 * Instances of BoardEvent are used to keep track of things happening on the board.
 * This class and all subclasses must be immutable.
 */
public abstract class BoardEvent {
  
  public final ChessBoard board;
  
  BoardEvent(ChessBoard board) {
    this.board = board;
  }
  
  /**
   * Signals that a turn has ended.
   */
  public static final class EndOfTurnEvent extends BoardEvent {
    
    EndOfTurnEvent(ChessBoard board) {
      super(board);
    }
  }
}

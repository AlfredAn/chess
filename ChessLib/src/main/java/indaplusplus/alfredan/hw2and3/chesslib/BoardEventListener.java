package indaplusplus.alfredan.hw2and3.chesslib;

public interface BoardEventListener {
  
  /**
   * Called whenever an event occurs on the board.
   */
  void boardEvent(BoardEvent event);
}

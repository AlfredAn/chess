package indaplusplus.alfredan.hw2and3.chesslib2;

public final class Board {
  
  private final Piece[][] board;
  
  public Board(MutableBoard otherBoard) {
    board = new Piece[otherBoard.getWidth()][otherBoard.getHeight()];
    
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        board[x][y] = otherBoard.get(x, y);
      }
    }
  }
  
  public Board(int width, int height) {
    board = new Piece[width][height];
  }
  
  public Piece get(int x, int y) {
    return board[x][y];
  }
  
  public int getWidth() {
    return board.length;
  }
  
  public int getHeight() {
    return board[0].length;
  }
  
  public boolean isValidPosition(int x, int y) {
    return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
  }
  
  public Board makeMove(int fromX, int fromY, int toX, int toY, MutableBoard mutableBoard) {
    Piece piece = get(fromX, fromY);
    if (piece == null) {
      throw new IllegalArgumentException("No piece here!");
    }
    
    mutableBoard.set(this);
    piece.makeMove(mutableBoard, fromX, fromY, toX, toY);
    
    return new Board(mutableBoard);
  }
}

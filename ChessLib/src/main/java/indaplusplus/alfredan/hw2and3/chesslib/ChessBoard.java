package indaplusplus.alfredan.hw2and3.chesslib;

public class ChessBoard {
  
  public final int width, height;
  
  protected final ChessPiece[][] board;
  
  /**
   * Create an empty ChessBoard with the specified dimensions.
   * @param width The width of the ChessBoard.
   * @param height The height of the ChessBoard.
   * @throws IllegalArgumentException if width or height <= 0
   */
  public ChessBoard(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid board size: " + width + "x" + height);
    }
    
    this.width = width;
    this.height = height;
    
    board = new ChessPiece[width][height];
  }
  
  /**
   * Create an empty 8x8 ChessBoard.
   */
  public ChessBoard() {
    this(8, 8);
  }
}

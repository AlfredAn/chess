package indaplusplus.alfredan.chesslib2;

import java.util.Arrays;

public final class ChessBoard {
  
  private final ChessPiece[][] board;
  
  public ChessBoard(ChessPiece[][] board) {
    // deep copy the array
    int height = board[0].length;
    this.board = new ChessPiece[board.length][];
    for (int i = 0; i < board.length; i++) {
      this.board[i] = Arrays.copyOf(board[i], board[i].length);
      if (board[i].length != height) {
        throw new IllegalArgumentException();
      }
    }
  }
  
  public ChessBoard(int width, int height) {
    board = new ChessPiece[width][height];
  }
  
  public ChessPiece getPiece(int x, int y) {
    return board[x][y];
  }
  
  
}

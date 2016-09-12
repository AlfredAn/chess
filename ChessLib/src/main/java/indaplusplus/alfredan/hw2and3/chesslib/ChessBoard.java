package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;

/**
 * Represents a chess board.
 * Can be of any size as long as it has at least one cell.
 */
public class ChessBoard {
  
  public final int width, height;
  
  private final ChessPiece[][] board;
  
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
  
  /**
   * Returns the chess piece at the specified position, or null if there is none.
   * @throws IllegalArgumentException if the position is invalid
   */
  public ChessPiece getPiece(int x, int y) {
    checkIfValidPosition(x, y);
    
    return board[x][y];
  }
  
  /**
   * Returns the chess piece at the specified position, or null if there is none.
   * @throws IllegalArgumentException if the position is invalid
   */
  public ChessPiece getPiece(IntVector2 position) {
    return getPiece(position.x, position.y);
  }
  
  /**
   * Places the given piece on the ChessBoard at the specified position.
   * If the piece is already on this or another board, it will be moved to the
   * specified position.
   * If there already is a piece at the target location, it will be captured
   * and the move will still take place.
   * @return Whether a piece was captured (i.e. whether the target location was
   * not empty)
   * @throws IllegalArgumentException if the position is not valid
   */
  public boolean placePiece(ChessPiece piece, int x, int y) {
    checkIfValidPosition(x, y);
    
    if (piece.getBoard() != null) {
      piece.getBoard().removePiece(piece);
    }
    
    boolean capture = false;
    
    if (board[x][y] != null) {
      removePiece(board[x][y]);
      capture = true;
    }
    
    board[x][y] = piece;
    
    piece.board = this;
    piece.xPos = x;
    piece.yPos = y;
    
    return capture;
  }
  
  /**
   * Places the given piece on the ChessBoard at the specified position.
   * If the piece is already on this or another board, it will be moved to the
   * specified position.
   * @throws IllegalArgumentException if the position is not valid
   */
  public void placePiece(ChessPiece piece, IntVector2 position) {
    placePiece(piece, position.x, position.y);
  }
  
  /**
   * Removes the specified piece from the board.
   * @throws IllegalArgumentException if the piece is not on this board
   */
  public void removePiece(ChessPiece piece) {
    if (piece.getBoard() != this) {
      throw new IllegalArgumentException("Can't remove a piece that isn't on the board.");
    } else if (getPiece(piece.xPos, piece.yPos) != piece) {
      throw new AssertionError("A piece has not been properly updated.");
    }
    
    board[piece.xPos][piece.yPos] = null;
    piece.board = null;
  }
  
  /**
   * Returns whether the specified position is within bounds.
   */
  public boolean isValidPosition(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }
  
  /**
   * Returns whether the specified position is within bounds.
   */
  public boolean isValidPosition(IntVector2 pos) {
    return isValidPosition(pos.x, pos.y);
  }
  
  /**
   * Throws an IllegalArgumentException if the specified coordinates are invalid.
   */
  private void checkIfValidPosition(int x, int y) {
    if (!isValidPosition(x, y)) {
      throw new IllegalArgumentException("Invalid board position: (" + x + ", " + y + ")");
    }
  }
}

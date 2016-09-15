package indaplusplus.alfredan.hw2and3.chesscmd;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Bishop;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.King;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Knight;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Pawn;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Rook;
import java.util.Locale;

public final class StandardChess {
  
  public StandardChess() {}
  
  private static final int BUF_WIDTH = 6 * 8 + 1 + 4;
  private static final int BUF_HEIGHT = 4 * 8 + 1 + 2;
  
  private final char[][] buf = new char[BUF_HEIGHT][BUF_WIDTH];
  private final StringBuilder sb = new StringBuilder();
  
  private final StandardChessGame game = new StandardChessGame();
  
  public void run() {
    System.out.println("\n\n-----Standard Chess-----\n");
    
    doTurn();
  }
  
  private void doTurn() {
    System.out.println("\nIt's " + Team.getTeamName(game.getTurn()).toLowerCase(Locale.ENGLISH) + "'s turn!\n");
    
    printChessBoard();
  }
  
  private void printChessBoard() {
    clear();
    
    Board board = game.getBoard();
    
    // draw the bottom edge
    for (int x = 2; x < BUF_WIDTH - 2; x++) {
      draw(x, BUF_HEIGHT - 2, '-');
    }
    
    // draw the right edge
    for (int y = 1; y < BUF_HEIGHT - 1; y++) {
      draw(BUF_WIDTH - 3, y, '|');
    }
    
    // for each cell on the chess board
    for (int cx = 0; cx < 8; cx++) {
      for (int cy = 0; cy < 8; cy++) {
        // draw top of cell
        for (int x = 0; x < 6; x++) {
          draw(2 + cx * 6 + x, 1 + cy * 4, '-');
        }
        
        // draw left part of cell
        for (int y = 0; y < 5; y++) {
          draw(2 + cx * 6, 1 + cy * 4 + y, '|');
        }
        
        //draw piece in the cell
        Piece piece = board.get(cx, 7-cy);
        draw(5 + cx * 6, 3 + cy * 4, getPieceLetter(piece));
        
        if (piece != null) {
          draw(5 + cx * 6, 2 + cy * 4, piece.team == Team.BLACK ? 'b' : 'w');
        }
      }
    }
    
    // draw the letters on top and bottom
    for (int cx = 0; cx < 8; cx++) {
      draw(5 + cx * 6, 0, (char)('a' + cx));
      draw(5 + cx * 6, BUF_HEIGHT - 1, (char)('a' + cx));
    }
    
    // draw the numbers on the left and right
    for (int cy = 0; cy < 8; cy++) {
      draw(0, 3 + cy * 4, (char)('8' - cy));
      draw(BUF_WIDTH - 1, 3 + cy * 4, (char)('0' + cy));
    }
    
    // add to StringBuilder and print to screen
    for (int y = 0; y < BUF_HEIGHT; y++) {
      sb.append(buf[y]).append('\n');
    }
    
    System.out.println(sb);
    sb.setLength(0);
  }
  
  private void draw(int x, int y, char c) {
    buf[y][x] = c;
  }
  
  private void clear() {
    for (int x = 0; x < BUF_WIDTH; x++) {
      for (int y = 0; y < BUF_HEIGHT; y++) {
        draw(x, y, ' ');
      }
    }
  }
  
  private static char getPieceLetter(Piece piece) {
    if (piece == null) {
      return ' ';
    } else if (piece instanceof Pawn) {
      return 'P';
    } else if (piece instanceof Knight) {
      return 'N';
    } else if (piece instanceof Bishop) {
      return 'B';
    } else if (piece instanceof Rook) {
      return 'R';
    } else if (piece instanceof Queen) {
      return 'Q';
    } else if (piece instanceof King) {
      return 'K';
    } else {
      return '?';
    }
  }
}

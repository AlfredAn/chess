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
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import indaplusplus.alfredan.hw2and3.chesslib.util.TextUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public final class StandardChess {
  
  public StandardChess() {}
  
  private static final int BUF_WIDTH = 6 * 8 + 1 + 4;
  private static final int BUF_HEIGHT = 4 * 8 + 1 + 2;
  
  private final char[][] buf = new char[BUF_HEIGHT][BUF_WIDTH];
  private final StringBuilder sb = new StringBuilder();
  
  private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  
  private StandardChessGame game = new StandardChessGame();
  
  private int xLook = -1, yLook = -1;
  
  public void run() {
    outer: while (true) {
      System.out.println("\n\n-----Standard Chess-----\n");
      System.out.println("Type help if you don't know what to do!");
      mid: while (true) {
        doTurn();
        
        switch (game.getGameStatus()) {
          case BLACK_WIN:
            xLook = -1;
            yLook = -1;
            printChessBoard();
            System.out.println("\nCheckmate! Black wins.");
            break mid;
          case WHITE_WIN:
            xLook = -1;
            yLook = -1;
            printChessBoard();
            System.out.println("\nCheckmate! White wins.");
            break mid;
          case DRAW:
            xLook = -1;
            yLook = -1;
            printChessBoard();
            System.out.println("\nThe game ended in a draw.");
            break mid;
        }
      }
      
      System.out.print("\n\nPlay again? (y/n)\n> ");
      while (true) {
        try {
          String s = reader.readLine();
          if (s.length() == 0) {
            continue;
          }
          char c = s.charAt(0);
          switch (c) {
            case 'y':
            case 'Y':
              System.out.print("\n\n");
              game = new StandardChessGame();
              continue outer;
            case 'n':
            case 'N':
              System.out.print("\n> ");
              return;
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
  
  private void doTurn() {
    xLook = -1;
    yLook = -1;
    
    printChessBoard();
    
    if (game.getBoard().isChecked(game.getTurn())) {
      System.out.println("Check!");
    }
    System.out.print("\nIt's " + Team.getTeamName(game.getTurn()).toLowerCase(Locale.ENGLISH) + "'s turn!\n> ");
    
    outer: while (true) {
      try {
        String[] input = reader.readLine().split(" ");
        if (input.length == 0) continue;
        
        // parse command
        switch (input[0].toLowerCase(Locale.ENGLISH)) {
          case "m":
          case "mo":
          case "mov":
          case "move":
            if (input.length != 3) {
              System.out.print("\nUsage: move xx yy\nWhere xx and yy are positions on the board, such as c1 or h5\n> ");
              break;
            }
            try {
              IntVector2 from = TextUtil.readSquareText(input[1]);
              IntVector2 to = TextUtil.readSquareText(input[2]);
              if (!game.getBoard().isValidPosition(from.x, from.y)
                      || !game.getBoard().isValidPosition(to.x, to.y)) {
                System.out.print("\nUsage: move xx yy\nWhere xx and yy are positions on the board, such as c1 or h5\n> ");
                break;
              }
              boolean succeeded = game.move(from.x, from.y, to.x, to.y);
              if (succeeded) {
                break outer;
              } else {
                System.out.print("\nThat move isn't valid.\n> ");
              }
            } catch (IllegalArgumentException e) {
              System.out.print("\nUsage: move xx yy\nWhere xx and yy are positions on the board, such as c1 or h5\n> ");
              break;
            }
            break;
          case "l":
          case "lo":
          case "loo":
          case "look":
            if (input.length > 2) {
              System.out.print("\nUsage: look xx\nWhere xx is the position of a piece on the board.\n> ");
              break;
            }
            int prevXLook = xLook;
            int prevYLook = yLook;
            if (input.length == 1) {
              xLook = -1;
              yLook = -1;
            } else {
              IntVector2 pos = TextUtil.readSquareText(input[1]);
              if (!game.getBoard().isValidPosition(pos.x, pos.y)) {
                System.out.print("\nUsage: look xx\nWhere xx is the position of a piece on the board.\n> ");
                break;
              }
              xLook = pos.x;
              yLook = pos.y;
            }
            if (prevXLook != xLook || prevYLook != yLook) {
              printChessBoard();
            }
            System.out.print("\n> ");
            break;
          case "h":
          case "he":
          case "hel":
          case "help":
            System.out.print("\nCommand list:"
                    + "\nmove xx yy - move the piece at xx to yy."
                    + "\nlook xx - display all possible moves that can be made from xx."
                    + "\nhelp - display this list.\nYou don't have to type the whole command names, only the first letter is needed.\n> ");
            break;
          default:
            System.out.print("\nUnknown command: " + input[0] + " (type \"help\" for a list of commands)\n> ");
            break;
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    
    if (game.canPromotePawn()) {
      printChessBoard();
    }
    outer: while (game.canPromotePawn()) {
      System.out.print("\nPawn promotion! Type one of the following letters to choose your piece:\n"
              + "Q: Queen\n"
              + "R: Rook\n"
              + "B: Bishop\n"
              + "N: Knight\n> ");
      try {
        String[] input = reader.readLine().split(" ");
        if (input.length == 0) {
          continue;
        }
        switch (input[0].toLowerCase(Locale.ENGLISH)) {
          case "q":
            game.promotePawn(Queen.class);
            break outer;
          case "r":
            game.promotePawn(Rook.class);
            break outer;
          case "b":
            game.promotePawn(Bishop.class);
            break outer;
          case "n":
            game.promotePawn(Knight.class);
            break outer;
          default:
            break;
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  private void printChessBoard() {
    clear();
    
    Board board = game.getBoard();
    
    List<IntVector2> moveList = null;
    if (board.isValidPosition(xLook, yLook)
            && board.get(xLook, yLook) != null) {
      moveList = board.getAvailableMoves(xLook, yLook);
    }
    
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
        
        // draw cross if this is in the moveList
        if (moveList != null && moveList.contains(new IntVector2(cx, 7-cy))) {
          draw(3 + cx * 6, 2 + cy * 4, '#');
          draw(4 + cx * 6, 2 + cy * 4, '#');
          draw(6 + cx * 6, 2 + cy * 4, '#');
          draw(7 + cx * 6, 2 + cy * 4, '#');
          draw(3 + cx * 6, 4 + cy * 4, '#');
          draw(4 + cx * 6, 4 + cy * 4, '#');
          draw(6 + cx * 6, 4 + cy * 4, '#');
          draw(7 + cx * 6, 4 + cy * 4, '#');
        }
        
        // draw piece in the cell
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
      draw(BUF_WIDTH - 1, 3 + cy * 4, (char)('8' - cy));
    }
    
    sb.append('\n');
    
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

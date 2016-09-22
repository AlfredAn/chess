package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import indaplusplus.alfredan.hw2and3.chesslib.Board;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import static indaplusplus.alfredan.hw2and3.core.ChessGame.HEIGHT;
import static indaplusplus.alfredan.hw2and3.core.ChessGame.WIDTH;
import java.util.List;

final class ChessBoardDrawer {
  
  private ChessBoardDrawer() {}
  
  private static final Color
          background = Color.valueOf("BAD877"),
          whiteSquare = Color.valueOf("FFCE9E"),
          blackSquare = Color.valueOf("D18B47");
  
  public static void drawChessBoard(Draw draw, OrthographicCamera cam, Board board, int x, int y, int width, int height, int mouseX, int mouseY, int hiddenX, int hiddenY, int turn) {
    
    draw.shapes.setProjectionMatrix(cam.combined);
    draw.enableBlending();
    draw.sprites.setProjectionMatrix(cam.combined);
    
    BitmapFont bitmapFont = Fonts.arial32;
    bitmapFont.setColor(Color.BLACK);
    
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
    String[] numbers = {"8", "7", "6", "5", "4", "3", "2", "1"};
    
    draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
    draw.shapes.setColor(background);
    draw.shapes.rect(0, 0, WIDTH, HEIGHT);
    draw.shapes.end();
    
    for (int i = 0; i < 8; i++) {
      draw.sprites.begin();
      Fonts.boardLabels.setColor(Color.BLACK);
      Fonts.boardLabels.draw(draw.sprites, numbers[i], 16, i * 64 + (y + 24), 0, Align.center, false);
      Fonts.boardLabels.draw(draw.sprites, numbers[i], 16 + x + 8 * 64, i * 64 + (y + 24), 0, Align.center, false);
      draw.sprites.end();
      
      for (int k = 0; k < 8; k++) {
        draw.sprites.begin();
        if (i == 0 || i == 7) {
          Fonts.boardLabels.setColor(Color.BLACK);
          if (i == 0) {
            Fonts.boardLabels.draw(draw.sprites, letters[k], 32 + x + k * 64, y + i * 64 - 24, 0, Align.center, false);
          } else {
            Fonts.boardLabels.draw(draw.sprites, letters[k], 32 + x + k * 64, y + i * 64 + 72, 0, Align.center, false);
          }
        }
        draw.sprites.end();
        
        draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
        
        boolean isWhite = (i + k) % 2 == 0;
        if (isWhite) {
          draw.shapes.setColor(whiteSquare);
          isWhite = false;
        } else {
          draw.shapes.setColor(blackSquare);
          isWhite = true;
        }
        draw.shapes.rect(x + k * 64, y + i * 64, 64, 64);
        draw.shapes.end();
        
        draw.sprites.begin();
        if (board.get(k, 7 - i) != null && (hiddenX != k || hiddenY != 7 - i)) {
          Sprite spr = Sprites.getChessPiece(board.get(k, 7 - i));
          spr.translate(x + k * 64, y + i * 64);
          spr.draw(draw.sprites);
        }
        draw.sprites.end();
      }
    }
    
    drawPossibleMoves(draw, board, x, y, mouseX, mouseY, turn);
  }
  
  private static void drawPossibleMoves(Draw draw, Board board, int x, int y, int mouseX, int mouseY, int turn) {
    if (mouseX == -1 || mouseY == -1) {
      return;
    }
    
    draw.enableBlending();
    draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
    
    if (board.get(mouseX, mouseY) != null) {
      List<IntVector2> vectorList = board.getAvailableMoves(mouseX, mouseY);
      for (int i = 0; i < vectorList.size(); i++) {
        IntVector2 vectorItem = vectorList.get(i);
        
        Piece piece = board.get(mouseX, mouseY);
        if (((piece.team == Team.WHITE && turn == Team.WHITE) || (piece.team == Team.BLACK && turn == Team.BLACK)) && turn != -1) {
          draw.shapes.setColor(0.9f, 0.2f, 0.2f, 0.7f);
        } else {
          draw.shapes.setColor(0.7f, 0.5f, 0.5f, 0.7f);
        }
        draw.shapes.rect(x + vectorItem.x * 64, y + (7 - vectorItem.y) * 64, 64, 64);
      }
    }
    
    draw.shapes.end();
  }
}

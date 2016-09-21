package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import indaplusplus.alfredan.hw2and3.chesslib.Board;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;


import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import static indaplusplus.alfredan.hw2and3.core.ChessGame.HEIGHT;
import static indaplusplus.alfredan.hw2and3.core.ChessGame.WIDTH;
import java.util.List;

final class ChessBoardDrawer {

  private static boolean isWhite = true;
  
  private static String text = "Test";

  private ChessBoardDrawer() {}

  public static void drawChessBoard(Draw draw, OrthographicCamera cam, Board board, int x, int y, int width, int height, int mouseX, int mouseY, int hiddenX, int hiddenY) {

    draw.shapes.setProjectionMatrix(cam.combined);
    draw.enableBlending();
    draw.sprites.setProjectionMatrix(cam.combined);
    
       
    BitmapFont bitmapFont = Fonts.arial32;
    bitmapFont.setColor(Color.BLACK);

    
    String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H"};
    String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8"};

    
   
    
    draw.shapes.begin(ShapeRenderer.ShapeType.Filled);    
    draw.shapes.setColor(Color.valueOf("#5EA1D4"));
    draw.shapes.rect(0, 0, WIDTH, HEIGHT);
    draw.shapes.end();
    

    for (int i = 0; i < 8; i++) {
      for (int k = 0; k < 8; k++) {
        
          
        draw.sprites.begin();
        Fonts.arial32.setColor(Color.BLACK);
        Fonts.arial32.draw(draw.sprites, text, WIDTH / 2, 100, 100, Align.center, false);
    
        draw.sprites.end();

        draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
        if (isWhite) {
          draw.shapes.setColor(Color.valueOf("FFCE9E"));
          isWhite = false;
        } else {
          draw.shapes.setColor(Color.valueOf("D18B47"));
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
      if (i % 2 == 1) {
        isWhite = true;
      } else {
        isWhite = false;
      }

    }
    
  
    draw.shapes.end();
    
    drawPossibleMoves(draw, board, x, y, mouseX, mouseY);

  }

  private static void drawPossibleMoves(Draw draw, Board board, int x, int y, int mouseX, int mouseY) {

    if (mouseX == -1 || mouseY == -1) {
      return;
    }

    draw.enableBlending();
    if (board.get(mouseX, mouseY) != null) {
    List<IntVector2> vectorList = board.getAvailableMoves(mouseX,  mouseY);
    for (int i = 0; i < vectorList.size(); i++) {
      IntVector2 vectorItem = vectorList.get(i);
      draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
      draw.shapes.setColor(0.9f, 0.2f, 0.2f, 0.6f);
      draw.shapes.rect(x + vectorItem.x * 64, y + (7 - vectorItem.y) * 64, 64, 64);
      draw.shapes.end();
    }
    
    
    }

  }
}

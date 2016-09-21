package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;

final class ChessBoardDrawer {

  private static boolean isWhite = true;

  private ChessBoardDrawer() {}

  public static void drawChessBoard(Draw draw, OrthographicCamera cam, Board board, int x, int y, int width, int height, int mouseX, int mouseY, int hiddenX, int hiddenY) {

    draw.shapes.setProjectionMatrix(cam.combined);

    draw.sprites.enableBlending();
    draw.sprites.setProjectionMatrix(cam.combined);

    for (int i = 0; i < 8; i++) {
      for (int k = 0; k < 8; k++) {
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
          draw.sprites.draw(Sprites.getChessPiece(board.get(k, 7 - i)), x + k * 64 + 1, y + i * 64 + 1, 60, 60);
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

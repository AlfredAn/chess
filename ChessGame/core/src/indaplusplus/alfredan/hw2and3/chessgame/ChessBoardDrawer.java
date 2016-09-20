package indaplusplus.alfredan.hw2and3.chessgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Rook;

final class ChessBoardDrawer {

  private static boolean isWhite = true;

  private ChessBoardDrawer() {
  }


  public static void drawChessBoard(Draw draw, OrthographicCamera cam, Board board, int x, int y, int width, int height, int mouseX, int mouseY) {
   
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
        if (board.get(k,7 - i) != null) {
        draw.sprites.draw(Sprites.getChessPiece(board.get(k, 7 - i)), x + k * 64 + 2, y + i * 64 - 2, 60, 60);
        }
        draw.sprites.end();
        
        
        
      }
      if (i % 2 == 1) {
        isWhite = true;
      } else {
        isWhite = false;
      }

    }
    
    




    //draw.shapes.




  }
}

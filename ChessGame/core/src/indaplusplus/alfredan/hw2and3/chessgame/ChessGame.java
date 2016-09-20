package indaplusplus.alfredan.hw2and3.chessgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame;

public class ChessGame extends ApplicationAdapter {
  
  public static final int WIDTH = 576, HEIGHT = 640;
  
  private Draw draw;
  
  public final OrthographicCamera cam = new OrthographicCamera();
  
  private static final int
          boardX = 32,
          boardY = 32,
          boardCellW = 64,
          boardCellH = 64,
          boardWidth = boardCellW * 8,
          boardHeight = boardCellH * 8;
  
  private int mouseX, mouseY;
  private int mouseBoardX = -1, mouseBoardY = -1;
  private int grabState = ST_OUTSIDE;
  
  private boolean leftPressed, leftDown;
  
  private StandardChessGame game = new StandardChessGame();
  
  private static final int
          ST_OUTSIDE = 0,
          ST_HOVER = 1,
          ST_GRABBING = 2;
  
  @Override
  public void create() {
    draw = new Draw();
  }
  
  private void update() {
    boolean newLeftDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    leftPressed = newLeftDown && !leftDown;
    leftDown = newLeftDown;
    
    mouseX = Gdx.input.getX();
    mouseY = Gdx.input.getY();
    
    boolean mouseOnBoard = mouseX >= boardX && mouseX < boardX + boardWidth
            && mouseY >= boardY && mouseY < boardY + boardHeight;
    
    if (grabState != ST_GRABBING) {
      if (mouseOnBoard) {
        mouseBoardX = (mouseX - boardX) / boardCellW;
        mouseBoardY = (mouseY - boardY) / boardCellH;

        if (game.getBoard().get(mouseBoardX, mouseBoardY) != null) {
          grabState = ST_HOVER;
        } else {
          grabState = ST_OUTSIDE;
        }
      } else {
        mouseBoardX = -1;
        mouseBoardY = -1;
        grabState = ST_OUTSIDE;
      }
    }
    
    if (grabState == ST_HOVER && leftPressed) {
      grabState = ST_GRABBING;
    }
    
    if (grabState == ST_GRABBING) {
      
    }
  }
  
  @Override
  public void render() {
    update();
    
    cam.setToOrtho(true, WIDTH, HEIGHT);
    cam.update();
    
    Gdx.gl.glClearColor(.75f, .75f, .75f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    draw.shapes.setProjectionMatrix(cam.combined);
    draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
    draw.shapes.setColor(Color.BLUE);
    draw.shapes.box(boardX, boardY, 0, boardCellW * 8, boardCellH * 8, 0);
    draw.shapes.end();
  }
  
  @Override
  public void dispose() {
    draw.dispose();
    draw = null;
  }
}

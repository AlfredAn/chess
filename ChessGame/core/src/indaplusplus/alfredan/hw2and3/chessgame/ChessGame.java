package indaplusplus.alfredan.hw2and3.chessgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame;

public class ChessGame extends ApplicationAdapter {
  
  public static final int WIDTH = 576, HEIGHT = 640;
  
  private Draw draw;
  
  public final OrthographicCamera cam = new OrthographicCamera();
  
  private static final int
          boardX = 32,
          boardY = 48,
          boardCellW = 64,
          boardCellH = 64,
          boardWidth = boardCellW * 8,
          boardHeight = boardCellH * 8;
  
  private int mouseX, mouseY;
  private int mouseBoardX = -1, mouseBoardY = -1;
  private int grabX = -1, grabY = -1;
  private boolean grabbing;
  
  private int grabMouseDX;
  private int grabMouseDY;
  
  private boolean leftPressed, leftDown;
  
  private StandardChessGame game = new StandardChessGame();
  
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
    
    if (mouseOnBoard) {
      mouseBoardX =  7 - (mouseX - boardX) / boardCellW;
      mouseBoardY = 7 - (mouseY - boardY) / boardCellH;
    } else {
      mouseBoardX = -1;
      mouseBoardY = -1;
    }
    
    boolean hovering = !grabbing && mouseOnBoard && game.getBoard().get(mouseBoardX, mouseBoardY) != null;
    
    if (hovering && leftPressed) {
      grabbing = true;
      grabX = mouseBoardX;
      grabY = mouseBoardY;
      
      grabMouseDX = boardX + boardCellW * (7 - grabX) - mouseX;
      grabMouseDY = boardY + boardCellH * (7 - grabY) - mouseY;
      
    } else if (grabbing && leftPressed) {
      // releasing piece
      if (mouseOnBoard) {
        // try to move the piece
        game.move(grabX, grabY, mouseBoardX, mouseBoardY);
      }
      grabbing = false;
    }
  }
  
  @Override
  public void render() {
    update();
    
    cam.setToOrtho(true, WIDTH, HEIGHT);
    cam.update();
    
    Gdx.gl.glClearColor(.75f, .75f, .75f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    drawHeader(draw);
    
    draw.shapes.setProjectionMatrix(cam.combined);
    draw.shapes.begin(ShapeRenderer.ShapeType.Filled);
    draw.shapes.setColor(Color.BLUE);
    draw.shapes.rect(boardX, boardY, boardCellW * 8, boardCellH * 8);
    draw.shapes.end();
    
    ChessBoardDrawer.drawChessBoard(draw, cam, game.getBoard(), boardX, boardY, boardCellW, boardCellH);
    
    if (grabbing) {
      Piece grabbedPiece = game.getBoard().get(grabX, grabY);
      
      draw.sprites.setProjectionMatrix(cam.combined);
      draw.sprites.begin();
      draw.sprites.enableBlending();
      draw.sprites.draw(Sprites.getChessPiece(grabbedPiece), mouseX + grabMouseDX, mouseY + grabMouseDY);
      draw.sprites.end();
    }
  }
  
  private void drawHeader(Draw draw) {
    String text = "undefined";
    Color col, textCol;
    
    if (game.getTurn() == Team.BLACK) {
      col = Color.BLACK;
      textCol = Color.WHITE;
    } else {
      col = Color.WHITE;
      textCol = Color.BLACK;
    }
    
    switch (game.getGameStatus()) {
      case NORMAL:
        if (game.getBoard().isChecked(game.getTurn())) {
          text = "Check!";
        } else if (game.getTurn() == Team.BLACK) {
          text = "It's black's turn!";
        } else {
          text = "It's white's turn!";
        }
        break;
      case BLACK_WIN:
        text = "Checkmate for black!";
        col = Color.BLACK;
        textCol = Color.WHITE;
        break;
      case WHITE_WIN:
        text = "Checkmate for white!";
        col = Color.WHITE;
        textCol = Color.BLACK;
        break;
      case DRAW:
        text = "Draw!";
        col = Color.GRAY;
        textCol = Color.BLACK;
        break;
    }
    
    draw.shapes.begin(ShapeType.Filled);
    
    draw.shapes.setColor(col);
    draw.shapes.rect(8, 8, WIDTH - 16, boardY - 16);
    
    draw.shapes.end();
    
    draw.sprites.setProjectionMatrix(cam.combined);
    draw.sprites.begin();
    draw.enableBlending();
    
    Fonts.arial32.setColor(Color.WHITE);
    Fonts.arial32.draw(draw.sprites, text, WIDTH / 2, boardY / 2, 0, Align.center, false);
    
    draw.sprites.end();
  }
  
  @Override
  public void dispose() {
    draw.dispose();
    draw = null;
  }
}

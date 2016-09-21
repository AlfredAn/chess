package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame;
import java.util.ArrayList;

public class ChessGame extends ApplicationAdapter implements ButtonListener {
  
  public static final int WIDTH = 512 + 64, HEIGHT = 768 + 32;
  
  private Draw draw;
  
  public final OrthographicCamera cam = new OrthographicCamera();
  
  private final ArrayList<Button> buttons = new ArrayList<>();
  
  private static final int
          boardX = 32,
          boardY = 32 + 32,
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
  
  private boolean leftPressed, leftDown, rightPressed, rightDown;
  
  private StandardChessGame game = new StandardChessGame();
  
  private String gameOverMsg = "undefined";
  
  private Button resignButton;
  
  private Menu menu;
  
  public ChessGame() {}
  
  @Override
  public void create() {
    draw = new Draw();
    draw.cam = cam;
    
    menu = new Menu(this);
    
    buttons.add(resignButton = new Button(this, "Resign", Fonts.arial32, boardX, boardY + boardHeight + 16 + 64, 128, 32));
  }
  
  private void update() {
    boolean newLeftDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    leftPressed = newLeftDown && !leftDown;
    leftDown = newLeftDown;
    
    boolean newrightDown = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    rightPressed = newrightDown && !rightDown;
    rightDown = newrightDown;
    
    if (menu != null) {
      menu.update(leftDown, leftPressed);
      return;
    }
    
    mouseX = Gdx.input.getX();
    mouseY = Gdx.input.getY();
    
    boolean mouseOnBoard = mouseX >= boardX && mouseX < boardX + boardWidth
            && mouseY >= boardY && mouseY < boardY + boardHeight;
    
    if (mouseOnBoard) {
      mouseBoardX = (mouseX - boardX) / boardCellW;
      mouseBoardY = 7 - (mouseY - boardY) / boardCellH;
    } else {
      mouseBoardX = -1;
      mouseBoardY = -1;
    }
    
    boolean hovering = !grabbing && mouseOnBoard && game.getBoard().get(mouseBoardX, mouseBoardY) != null;
    
    if (hovering && leftPressed
            && game.getTurn() == game.getBoard().get(mouseBoardX, mouseBoardY).team
            && !game.getBoard().getAvailableMoves(mouseBoardX, mouseBoardY).isEmpty()) {
      grabbing = true;
      grabX = mouseBoardX;
      grabY = mouseBoardY;
      
      grabMouseDX = boardX + boardCellW * grabX - mouseX;
      grabMouseDY = boardY + boardCellH * (7 - grabY) - mouseY;
    } else if (grabbing && leftPressed) {
      // releasing piece
      if (mouseOnBoard) {
        // try to move the piece
        game.move(grabX, grabY, mouseBoardX, mouseBoardY);
      }
      grabbing = false;
    } else if (grabbing && rightPressed) {
      grabbing = false;
    }
    
    if (!grabbing) {
      grabX = -1;
      grabY = -1;
    }
    
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
  }
  
  @Override
  public void press(Button button) {}
  
  @Override
  public void render() {
    update();
    
    cam.setToOrtho(true, WIDTH, HEIGHT);
    cam.update();
    
    Gdx.gl.glClearColor(.75f, .75f, .75f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    if (menu != null) {
      menu.draw(draw);
      return;
    }
    
    ChessBoardDrawer.drawChessBoard(draw, cam, game.getBoard(), boardX, boardY, boardCellW, boardCellH,
            grabbing ? grabX : mouseBoardX, grabbing ? grabY : mouseBoardY, grabX, grabY, game.getTurn());
    
    drawHeader(draw);
    
    if (grabbing) {
      Piece grabbedPiece = game.getBoard().get(grabX, grabY);
      
      draw.sprites.setProjectionMatrix(cam.combined);
      draw.sprites.begin();
      draw.sprites.enableBlending();
      Sprite spr = Sprites.getChessPiece(grabbedPiece);
      spr.translate(mouseX + grabMouseDX, mouseY + grabMouseDY);
      spr.draw(draw.sprites);
      draw.sprites.end();
    }
    
    for (Button button : buttons) {
      button.draw(draw);
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
    draw.shapes.rect(0, 0, WIDTH, 32);
    
    draw.shapes.end();
    
    draw.sprites.setProjectionMatrix(cam.combined);
    draw.enableBlending();
    draw.sprites.begin();
    
    Fonts.arial32.setColor(textCol);
    Fonts.arial32.draw(draw.sprites, text, WIDTH / 2, 16 - 9, 0, Align.center, false);
    
    draw.sprites.end();
  }
  
  @Override
  public void dispose() {
    draw.dispose();
    draw = null;
  }
}

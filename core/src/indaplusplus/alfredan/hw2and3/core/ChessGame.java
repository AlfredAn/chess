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
  
  public static final int WIDTH = 576, HEIGHT = 768;
  
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
  
  private StandardChessGame game;
  
  private String gameOverMsg;
  
  private Button resignButton, restartButton, exitToMenuButton;
  
  private Menu menu;
  
  public ChessGame() {}
  
  @Override
  public void create() {
    draw = new Draw();
    draw.cam = cam;
    
    //menu = new Menu(this);
    
    startGame();
    
    resignButton = new Button(this, "Resign", Fonts.arial32, boardX, boardY + boardHeight + 112, boardWidth, 48);
    
    restartButton = new Button(this, "Restart", Fonts.arial32, boardX, boardY + boardHeight + 112, boardWidth / 2 - 8, 48);
    exitToMenuButton = new Button(this, "Exit to menu", Fonts.arial32, boardX + boardWidth / 2 + 8, boardY + boardHeight + 112, boardWidth / 2 - 8, 48);
    
    menu = new Menu(this);
  }
  
  private void startGame() {
    game = new StandardChessGame();
    gameOverMsg = null;
  }
  
  private void addIngameUI() {
    buttons.clear();
    buttons.add(resignButton);
  }
  
  private void addGameOverUI() {
    buttons.clear();
    buttons.add(restartButton);
    buttons.add(exitToMenuButton);
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
    
    if (game.getGameStatus() != StandardChessGame.GameStatus.NORMAL) {
      addGameOverUI();
    } else {
      addIngameUI();
    }
    
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
  }
  
  @Override
  public void press(Button button) {
    if (button == resignButton) {
      game.resign();
      gameOverMsg = Team.getTeamName(1 - game.getTurn()) + " wins by resignation!";
    } else if (button == restartButton) {
      startGame();
    } else if (button == exitToMenuButton) {
      menu = new Menu(this);
    }
  }
  
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
            grabbing ? grabX : mouseBoardX, grabbing ? grabY : mouseBoardY, grabX, grabY,
            game.getGameStatus() == StandardChessGame.GameStatus.NORMAL ? game.getTurn() : -1);
    
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
    
    // draw turn count
    draw.sprites.begin();
    draw.sprites.enableBlending();
    Fonts.arial32.setColor(Color.BLACK);
    Fonts.arial32.draw(draw.sprites, "Turn " + (game.getMoveCounter() + 1) + ", " + Team.getTeamName(game.getTurn()).toLowerCase(), boardX + boardWidth / 2, boardY + boardHeight + 56, 0, Align.center, false);
    draw.sprites.end();
    
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
    
    if (gameOverMsg == null && game.getGameStatus() != StandardChessGame.GameStatus.NORMAL) {
      gameOverMsg = text;
    } else if (gameOverMsg != null) {
      text = gameOverMsg;
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

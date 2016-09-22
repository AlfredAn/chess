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
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessAI;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame;
import java.util.ArrayList;
import java.util.List;

public class ChessGame extends ApplicationAdapter implements ButtonListener {
  
  public static final int WIDTH = 608, HEIGHT = 768;
  
  private Draw draw;
  
  public final OrthographicCamera cam = new OrthographicCamera();
  
  private final List<Button> buttons = new ArrayList<>();
  
  private static final int
          boardX = 32 + 16,
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
  
  private boolean
          leftPressed, leftReleased, leftDown,
          rightPressed, rightReleased, rightDown;
  
  private StandardChessGame game;
  
  private String gameOverMsg;
  
  private Button resignButton, restartButton, exitToMenuButton;
  
  StandardChessAI[] ai = new StandardChessAI[2];
  
  private double aiMoveTimer = 0;
  
  private Menu menu;
  PawnPromotionInterface pawnPromo;
  
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
  
  void startGame() {
    game = new StandardChessGame();
    gameOverMsg = null;
    menu = null;
    pawnPromo = null;
    
    makeMoveIfAI();
  }
  
  private void makeMoveIfAI() {
    // delay AI moves by 500ms to give the player a chance to see what is going on
    if (ai[game.getTurn()] == null || aiMoveTimer < 0.5 || game.getGameStatus() != StandardChessGame.GameStatus.NORMAL) {
      return;
    }
    
    ai[game.getTurn()].makeMove(game);
    grabbing = false;
    aiMoveTimer = 0;
    
    pawnPromo = null;
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
    leftReleased = !newLeftDown && leftDown;
    leftDown = newLeftDown;
    
    boolean newRightDown = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    rightPressed = newRightDown && !rightDown;
    rightReleased = !newRightDown && rightDown;
    rightDown = newRightDown;
    
    if (menu != null) {
      menu.update(leftDown, leftPressed);
      return;
    }
    
    updateMouse();
    
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
    
    if (hovering && leftPressed && ai[game.getTurn()] == null
            && game.getTurn() == game.getBoard().get(mouseBoardX, mouseBoardY).team
            && !game.getBoard().getAvailableMoves(mouseBoardX, mouseBoardY).isEmpty()
            && !game.canPromotePawn()) {
      grabbing = true;
      grabX = mouseBoardX;
      grabY = mouseBoardY;
      
      grabMouseDX = boardX + boardCellW * grabX + 32 - mouseX;
      grabMouseDY = boardY + boardCellH * (7 - grabY) + 32 - mouseY;
    } else if (grabbing && !leftDown) {
      // releasing piece
      if (mouseOnBoard) {
        // try to move the piece
        if (game.move(grabX, grabY, mouseBoardX, mouseBoardY)) {
          // if the move succeeded
          aiMoveTimer = 0;
          
          if (game.canPromotePawn()) {
            pawnPromo = new PawnPromotionInterface(
                    this,
                    boardX + boardCellW / 2 + boardCellW * mouseBoardX,
                    boardY + boardCellH / 2 + boardCellH * (7 - mouseBoardY),
                    mouseBoardX,
                    mouseBoardY);
          } else {
            pawnPromo = null;
          }
        }
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
    
    aiMoveTimer += Gdx.graphics.getDeltaTime();
    
    makeMoveIfAI();
    
    if (ai[0] == null || ai[1] == null) {
      resignButton.text = "Resign";
    } else {
      resignButton.text = "Stop";
    }
    
    if (pawnPromo != null) {
      pawnPromo.update(leftDown, leftPressed);
    }
    
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
    
    updateMouse();
    
    if (grabbing && !Gdx.input.isCursorCatched()) {
      //Gdx.input.setCursorCatched(true);
    } else if (!grabbing && Gdx.input.isCursorCatched()) {
      //Gdx.input.setCursorCatched(false);
      //Gdx.input.setCursorPosition(mouseX, mouseY);
    }
  }
  
  private void updateMouse() {
    mouseX = Gdx.input.getX();
    mouseY = Gdx.input.getY();
    
    if (grabbing) {
      mouseX += grabMouseDX;
      mouseY += grabMouseDY;
    }
  }
  
  @Override
  public void press(Button button) {
    if (button == resignButton) {
      if (ai[0] == null || ai[1] == null) {
        if (ai[game.getTurn()] == null) {
          game.resign();
          gameOverMsg = Team.getTeamName(1 - game.getTurn()) + " wins by resignation!";
        } else {
          game.resignOther();
          gameOverMsg = Team.getTeamName(game.getTurn()) + " wins by resignation!";
        }
      } else {
        game.declareDraw();
        gameOverMsg = "Game stopped prematurely!";
      }
    } else if (button == restartButton) {
      startGame();
    } else if (button == exitToMenuButton) {
      menu = new Menu(this);
    }
  }
  
  StandardChessGame getGame() {
    return game;
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
            (game.getGameStatus() == StandardChessGame.GameStatus.NORMAL && ai[game.getTurn()] == null && !game.canPromotePawn()) ? game.getTurn() : -1);
    
    drawHeader(draw);
    
    // draw turn count
    draw.sprites.begin();
    draw.sprites.enableBlending();
    Fonts.arial32.setColor(Color.BLACK);
    Fonts.arial32.draw(draw.sprites, "Turn " + (game.getMoveCounter() + 1) + ", " + Team.getTeamName(game.getTurn()).toLowerCase(), boardX + boardWidth / 2, boardY + boardHeight + 56, 0, Align.center, false);
    draw.sprites.end();
    
    for (Button button : buttons) {
      button.draw(draw);
    }
    
    if (pawnPromo != null) {
      pawnPromo.draw(draw);
    }
    
    if (grabbing) {
      Piece grabbedPiece = game.getBoard().get(grabX, grabY);
      
      draw.sprites.setProjectionMatrix(cam.combined);
      draw.sprites.begin();
      draw.sprites.enableBlending();
      Sprite spr = Sprites.getChessPiece(grabbedPiece);
      spr.translate(mouseX - 32, mouseY - 32);
      spr.draw(draw.sprites);
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
        if (game.canPromotePawn()) {
          text = "Pawn promotion!";
        } else if (game.getBoard().isChecked(game.getTurn())) {
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

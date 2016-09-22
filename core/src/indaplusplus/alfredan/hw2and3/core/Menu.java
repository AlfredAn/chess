package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import indaplusplus.alfredan.hw2and3.chesslib.game.RandomAI;
import java.util.ArrayList;
import java.util.List;

public class Menu implements ButtonListener {
  
  public static final int P_HUMAN = 0, P_AI = 1;
  
  private static int blackPlayer = P_AI, whitePlayer = P_HUMAN;
  
  private final List<Button> buttons = new ArrayList<>();
  
  private final ChessGame game;
  
  private final Button
          blackHumanButton,
          blackAiButton,
          whiteHumanButton,
          whiteAiButton,
          
          startGameButton,
          exitButton;
  
  public Menu(ChessGame game) {
    this.game = game;
    
    buttons.add(blackHumanButton = new Button(this, "Human", Fonts.arial32, ChessGame.WIDTH / 2 - 128, 192, 120, 48));
    buttons.add(blackAiButton    = new Button(this, "AI"   , Fonts.arial32, ChessGame.WIDTH / 2 +   8, 192, 120, 48));
    
    buttons.add(whiteHumanButton = new Button(this, "Human", Fonts.arial32, ChessGame.WIDTH / 2 - 128, 312, 120, 48));
    buttons.add(whiteAiButton    = new Button(this, "AI"   , Fonts.arial32, ChessGame.WIDTH / 2 +   8, 312, 120, 48));
    
    buttons.add(startGameButton  = new Button(this, "Start Game", Fonts.arial32, ChessGame.WIDTH / 2 - 192, 516, 384, 48));
    buttons.add(exitButton       = new Button(this, "Exit"      , Fonts.arial32, ChessGame.WIDTH / 2 - 192, 580, 384, 48));
  }
  
  void update(boolean leftDown, boolean leftPressed) {
    blackHumanButton.marked = blackPlayer == P_HUMAN;
    blackAiButton   .marked = blackPlayer == P_AI;
    
    whiteHumanButton.marked = whitePlayer == P_HUMAN;
    whiteAiButton   .marked = whitePlayer == P_AI;
    
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
  }
  
  void draw(Draw d) {
    d.sprites.begin();
    d.sprites.setProjectionMatrix(d.cam.combined);
    d.sprites.enableBlending();
    
    // draw title
    Fonts.title.setColor(Color.BLACK);
    Fonts.title.draw(d.sprites, "ChessGame", ChessGame.WIDTH / 2, 48, 0, Align.center, false);
    
    // black player label
    Fonts.arial32.setColor(Color.BLACK);
    Fonts.arial32.draw(d.sprites, "Black player:", ChessGame.WIDTH / 2, 160, 0, Align.center, false);
    
    // white player label
    Fonts.arial32.setColor(Color.BLACK);
    Fonts.arial32.draw(d.sprites, "White player:", ChessGame.WIDTH / 2, 280, 0, Align.center, false);
    
    // AI information
    Fonts.arial32.setColor(Color.BLACK);
    Fonts.arial32.draw(d.sprites, "The AI will simply make random moves.", ChessGame.WIDTH / 2, 400, 0, Align.center, false);
    
    d.sprites.end();
    
    for (Button button : buttons) {
      button.draw(d);
    }
  }
  
  @Override
  public void press(Button button) {
    if (button == blackHumanButton) {
      blackPlayer = P_HUMAN;
    } else if (button == blackAiButton) {
      blackPlayer = P_AI;
    } else if (button == whiteHumanButton) {
      whitePlayer = P_HUMAN;
    } else if (button == whiteAiButton) {
      whitePlayer = P_AI;
    } else if (button == startGameButton) {
      game.ai[0] = (blackPlayer == P_HUMAN) ? null : new RandomAI();
      game.ai[1] = (whitePlayer == P_HUMAN) ? null : new RandomAI();
      game.startGame();
    } else if (button == exitButton) {
      Gdx.app.exit();
    }
  }
}

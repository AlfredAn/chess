package indaplusplus.alfredan.hw2and3.core;

import indaplusplus.alfredan.hw2and3.chesslib.Team;
import static indaplusplus.alfredan.hw2and3.core.ChessGame.WIDTH;
import java.util.ArrayList;

public class Menu implements ButtonListener {
  
  private final ArrayList<Button> buttons = new ArrayList<>();
  
  private final ChessGame game;
  
  private int marked;
  
  public Menu(ChessGame game) {
    this.game = game;
    
    Button start = new Button(this, "START", Fonts.arial32, WIDTH / 2 - 128, 200, 256, 32);
    Button playerWhiteButton = new Button(this, "WHITE", Fonts.arial32, WIDTH / 2, 232, 128, 32);
    Button playerBlackButton = new Button(this, "BLACK", Fonts.arial32, WIDTH / 2 - 128, 232, 128, 32);
    Button exit = new Button(this, "EXIT", Fonts.arial32, WIDTH / 2 - 128, 264, 256, 32);
    
    buttons.add(start);
    buttons.add(playerWhiteButton);
    buttons.add(playerBlackButton);
    buttons.add(exit);
  }
  
  void update(boolean leftDown, boolean leftPressed) {
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
  }
  
  void draw(Draw d) {
    for (Button button : buttons) {
      if (button.text.equals("WHITE") && marked == Team.WHITE || button.text.equals("BLACK") && marked == Team.BLACK) {
        button.marked = true;
      } else {
        button.marked = false;
      }
      button.draw(d);
    }
  }
  
  @Override
  public void press(Button button) {
    if (button.text.equals("WHITE")) {
      marked = Team.WHITE;
    } else if (button.text.equals("BLACK")) {
      marked = Team.BLACK;
    }
  }
}

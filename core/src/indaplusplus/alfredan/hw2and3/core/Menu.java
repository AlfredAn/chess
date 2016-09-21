package indaplusplus.alfredan.hw2and3.core;

import java.util.ArrayList;

public class Menu implements ButtonListener {
  
  private final ArrayList<Button> buttons = new ArrayList<>();
  
  private final ChessGame game;
  
  public Menu(ChessGame game) {
    this.game = game;
  }
  
  void update(boolean leftDown, boolean leftPressed) {
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
  }
  
  void draw(Draw d) {
    for (Button button : buttons) {
      button.draw(d);
    }
  }
  
  @Override
  public void press(Button button) {
    
  }
}

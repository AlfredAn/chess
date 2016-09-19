package indaplusplus.alfredan.hw2and3.chessgame.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import indaplusplus.alfredan.hw2and3.chessgame.ChessGame;

public class HtmlLauncher extends GwtApplication {
  
  @Override
  public GwtApplicationConfiguration getConfig() {
    return new GwtApplicationConfiguration(ChessGame.WIDTH, ChessGame.HEIGHT);
  }
  
  @Override
  public ApplicationListener createApplicationListener() {
    return new ChessGame();
  }
}

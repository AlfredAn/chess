package indaplusplus.alfredan.hw2and3.html;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import indaplusplus.alfredan.hw2and3.core.ChessGame;

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

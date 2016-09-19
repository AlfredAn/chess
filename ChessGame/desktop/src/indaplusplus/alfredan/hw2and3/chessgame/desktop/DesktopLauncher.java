package indaplusplus.alfredan.hw2and3.chessgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import indaplusplus.alfredan.hw2and3.chessgame.ChessGame;

public class DesktopLauncher {
  
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    
    config.fullscreen = false;
    config.width = ChessGame.WIDTH;
    config.height = ChessGame.HEIGHT;
    config.resizable = false;
    config.title = "ChessGame";
    
    new LwjglApplication(new ChessGame(), config);
  }
}

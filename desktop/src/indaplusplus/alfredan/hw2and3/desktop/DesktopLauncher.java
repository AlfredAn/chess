package indaplusplus.alfredan.hw2and3.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import indaplusplus.alfredan.hw2and3.core.ChessGame;

public class DesktopLauncher {
  
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    
    config.fullscreen = false;
    config.width = ChessGame.WIDTH;
    config.height = ChessGame.HEIGHT;
    config.resizable = false;
    config.title = "ChessGame";
    
    config.foregroundFPS = 0;
    config.backgroundFPS = 0;
    config.vSyncEnabled = false;
    
    new LwjglApplication(new ChessGame(), config);
  }
}

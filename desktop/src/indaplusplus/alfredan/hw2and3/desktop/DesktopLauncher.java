package indaplusplus.alfredan.hw2and3.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
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
    
    DisplayMode dm = LwjglApplicationConfiguration.getDesktopDisplayMode();
    
    config.foregroundFPS = dm.refreshRate;
    config.backgroundFPS = dm.refreshRate;
    config.vSyncEnabled = true;
    
    new LwjglApplication(new ChessGame(), config);
  }
}

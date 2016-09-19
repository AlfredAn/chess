package indaplusplus.alfredan.hw2and3.chessgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ChessGame extends ApplicationAdapter {
  
  public static final int WIDTH = 576, HEIGHT = 640;
  
  @Override
  public void create() {
  }
  
  @Override
  public void render() {
    Gdx.gl.glClearColor(.75f, .75f, .75f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }
  
  @Override
  public void dispose() {
  }
}

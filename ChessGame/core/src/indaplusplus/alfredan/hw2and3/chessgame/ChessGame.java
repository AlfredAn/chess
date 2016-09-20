package indaplusplus.alfredan.hw2and3.chessgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ChessGame extends ApplicationAdapter {
  
  public static final int WIDTH = 576, HEIGHT = 640;
  
  private Draw draw;
  
  public final OrthographicCamera cam = new OrthographicCamera();
  
  @Override
  public void create() {
    draw = new Draw();
  }
  
  private void update() {
    
  }
  
  @Override
  public void render() {
    update();
    
    cam.setToOrtho(true, WIDTH, HEIGHT);
    
    Gdx.gl.glClearColor(.75f, .75f, .75f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }
  
  @Override
  public void dispose() {
    draw.dispose();
    draw = null;
  }
}

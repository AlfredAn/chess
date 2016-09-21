package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class Button {
  
  public final int x, y, width, height;
  
  public final BitmapFont font;
  public final String text;
  
  public Button(String text, BitmapFont font, int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    
    this.text = text;
    this.font = font;
  }
  
  public void draw(Draw d) {
    d.shapes.begin(ShapeRenderer.ShapeType.Filled);
    d.shapes.setColor(.25f, .25f, .75f, 1.0f);
    d.shapes.rect(x, y, width, height);
    d.shapes.end();
    
    d.enableBlending();
    d.sprites.begin();
    font.setColor(Color.BLACK);
    font.draw(d.sprites, text, x + width / 2, y + height / 2, 0, Align.center, false);
    d.sprites.end();
    
    d.shapes.begin(ShapeRenderer.ShapeType.Line);
    d.shapes.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    d.shapes.rect(x, y, width, height);
    d.shapes.end();
  }
}

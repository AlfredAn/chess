package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

public class Button {
  
  public final int x, y, width, height;
  
  public final BitmapFont font;
  public final String text;
  
  private boolean hovering, pressing;
  
  public Button(String text, BitmapFont font, int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    
    this.text = text;
    this.font = font;
  }
  
  public void update(boolean leftDown, boolean leftPressed) {
    int mouseX = Gdx.input.getX();
    int mouseY = Gdx.input.getY();
    
    hovering = mouseX >= x && mouseX < x + width
            && mouseY >= y && mouseY < y + height;
    
    if (hovering && !pressing && leftPressed) {
      pressing = true;
    }
    
    if (pressing && !leftDown) {
      if (hovering) {
        press();
      }
      pressing = false;
    }
  }
  
  private void press() {
    
  }
  
  public void draw(Draw d) {
    d.shapes.setProjectionMatrix(d.cam.combined);
    d.shapes.begin(ShapeRenderer.ShapeType.Filled);
    d.shapes.setColor(.25f, .25f, .75f, 1.0f);
    d.shapes.rect(x, y, width, height);
    d.shapes.end();
    
    d.enableBlending();
    d.sprites.setProjectionMatrix(d.cam.combined);
    d.sprites.begin();
    font.setColor(Color.BLACK);
    font.draw(d.sprites, text, x + width / 2, y + height / 2 - 9, 0, Align.center, false);
    d.sprites.end();
    
    if (hovering) {
      d.enableBlending();
      d.shapes.setProjectionMatrix(d.cam.combined);
      d.shapes.begin(ShapeRenderer.ShapeType.Filled);
      if (pressing) {
        d.shapes.setColor(0, 0, 0, .4f);
      } else {
        d.shapes.setColor(1, 1, 1, .3f);
      }
      d.shapes.rect(x, y, width, height);
      d.shapes.end();
    }
    
    d.shapes.setProjectionMatrix(d.cam.combined);
    d.shapes.begin(ShapeRenderer.ShapeType.Line);
    d.shapes.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    d.shapes.rect(x, y, width, height);
    d.shapes.end();
  }
}

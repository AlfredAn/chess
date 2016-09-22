package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteButton extends Button {
  
  private final Sprite spr;
  
  public SpriteButton(ButtonListener listener, Sprite spr, int x, int y, int width, int height) {
    super(listener, "", null, x, y, width, height);
    
    this.spr = spr;
  }
  
  @Override
  public void drawContents(Draw d) {
    d.sprites.begin();
    d.enableBlending();
    d.sprites.setProjectionMatrix(d.cam.combined);
    
    spr.setBounds(x, y, width, height);
    spr.draw(d.sprites);
    spr.setBounds(0, 0, 60, 60);
    
    d.sprites.end();
  }
}

package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public final class Fonts {
  
  private Fonts() {}
  
  public static final BitmapFont
          arial32 = new BitmapFont(Gdx.files.internal("fonts/arial32.fnt"), true),
          boardLabels = new BitmapFont(Gdx.files.internal("fonts/boardlabels.fnt"), true);
}

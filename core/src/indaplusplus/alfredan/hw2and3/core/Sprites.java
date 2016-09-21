package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Bishop;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.King;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Knight;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Pawn;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Rook;
import java.util.HashMap;
import java.util.Map;

public final class Sprites {
  
  private Sprites() {}
  
  private static final Map<Class<? extends Piece>, SpriteContainer> black = new HashMap<>();
  private static final Map<Class<? extends Piece>, SpriteContainer> white = new HashMap<>();
  
  private static final int
          PIECE_DX = 1,
          PIECE_DY = 1;
  
  static {
    black.put(Pawn.class,   load("black_pawn",   0, 0));
    black.put(Knight.class, load("black_knight", 0, 0));
    black.put(Rook.class,   load("black_rook",   0, 0));
    black.put(Bishop.class, load("black_bishop", 0, 0));
    black.put(Queen.class,  load("black_queen",  1, 0));
    black.put(King.class,   load("black_king",   0, 0));
    
    white.put(Pawn.class,   load("white_pawn",   0, 0));
    white.put(Knight.class, load("white_knight", 0, 0));
    white.put(Rook.class,   load("white_rook",   0, 0));
    white.put(Bishop.class, load("white_bishop", 0, 0));
    white.put(Queen.class,  load("white_queen",  1, 0));
    white.put(King.class,   load("white_king",   0, 0));
  }
  
  private static SpriteContainer load(String name, int dx, int dy) {
    Sprite sprite = new Sprite(new Texture("sprites/" + name + ".png"));
    
    // flip upside down
    sprite.flip(false, true);
    
    return new SpriteContainer(sprite, dx + PIECE_DX, dy + PIECE_DY);
  }
  
  public static Sprite getChessPiece(Piece piece) {
    if (piece.team == Team.BLACK) {
      return black.get(piece.getClass()).getSpr();
    } else {
      return white.get(piece.getClass()).getSpr();
    }
  }
  
  private static final class SpriteContainer {
    private final Sprite spr;
    private final int dx, dy;
    
    private SpriteContainer(Sprite spr, int dx, int dy) {
      this.spr = spr;
      this.dx = dx;
      this.dy = dy;
    }
    
    private Sprite getSpr() {
      spr.setPosition(dx, dy);
      return spr;
    }
  }
}

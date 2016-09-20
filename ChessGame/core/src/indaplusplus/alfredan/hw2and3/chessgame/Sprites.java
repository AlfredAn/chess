package indaplusplus.alfredan.hw2and3.chessgame;

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

public class Sprites {
  
  private Sprites() {}
  
  private static final Map<Class<? extends Piece>, Sprite> black = new HashMap<>();
  private static final Map<Class<? extends Piece>, Sprite> white = new HashMap<>();
  
  static {
    black.put(Pawn.class, load("black_pawn"));
    black.put(Knight.class, load("black_knight"));
    black.put(Rook.class, load("black_rook"));
    black.put(Bishop.class, load("black_bishop"));
    black.put(Queen.class, load("black_queen"));
    black.put(King.class, load("black_king"));
    
    white.put(Pawn.class, load("white_pawn"));
    white.put(Knight.class, load("white_knight"));
    white.put(Rook.class, load("white_rook"));
    white.put(Bishop.class, load("white_bishop"));
    white.put(Queen.class, load("white_queen"));
    white.put(King.class, load("white_king"));
  }
  
  private static Sprite load(String name) {
    Sprite sprite = new Sprite(new Texture("sprites/" + name + ".png"));
    
    // flip upside down
    sprite.flip(false, true);
    
    return sprite;
  }
  
  public static Sprite getChessPiece(Piece piece) {
    if (piece.team == Team.BLACK) {
      return black.get(piece.getClass());
    } else {
      return white.get(piece.getClass());
    }
  }
}

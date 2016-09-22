package indaplusplus.alfredan.hw2and3.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Bishop;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Knight;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public final class PawnPromotionInterface implements ButtonListener {
  
  private final List<Button> buttons = new ArrayList<>();
  
  private final ChessGame game;
  private final int x, y;
  
  private final boolean flipped;
  
  private Button
          queenButton,
          bishopButton,
          rookButton,
          knightButton;
  
  public PawnPromotionInterface(ChessGame game, int x, int y, int boardX, int boardY) {
    this.game = game;
    
    this.x = x;
    this.y = y;
    
    int team = game.getGame().getTurn();
    
    flipped = (team == Team.BLACK);
    
    if (flipped) {
      buttons.add(queenButton   = new SpriteButton(this, Sprites.getChessPiece(Queen.class,  team), x - 72, y + 20, 30, 30));
      buttons.add(bishopButton  = new SpriteButton(this, Sprites.getChessPiece(Bishop.class, team), x - 34, y + 20, 30, 30));
      buttons.add(rookButton    = new SpriteButton(this, Sprites.getChessPiece(Rook.class,   team), x +  4, y + 20, 30, 30));
      buttons.add(knightButton  = new SpriteButton(this, Sprites.getChessPiece(Knight.class, team), x + 42, y + 20, 30, 30));
    } else {
      buttons.add(queenButton   = new SpriteButton(this, Sprites.getChessPiece(Queen.class,  team), x - 72, y - 50, 30, 30));
      buttons.add(bishopButton  = new SpriteButton(this, Sprites.getChessPiece(Bishop.class, team), x - 34, y - 50, 30, 30));
      buttons.add(rookButton    = new SpriteButton(this, Sprites.getChessPiece(Rook.class,   team), x +  4, y - 50, 30, 30));
      buttons.add(knightButton  = new SpriteButton(this, Sprites.getChessPiece(Knight.class, team), x + 42, y - 50, 30, 30));
    }
  }
  
  void update(boolean leftDown, boolean leftPressed) {
    for (Button button : buttons) {
      button.update(leftDown, leftPressed);
    }
  }
  
  void draw(Draw d) {
    d.enableBlending();
    d.shapes.setProjectionMatrix(d.cam.combined);
    
    d.shapes.begin(ShapeRenderer.ShapeType.Filled);
    d.shapes.setColor(.5f, .5f, .5f, .5f);
    
    if (flipped) {
      d.shapes.rect(x - 76, y + 16, 76 * 2, 54 - 16);
      d.shapes.triangle(x, y, x - 16, y + 16, x + 16, y + 16);
    } else {
      d.shapes.rect(x - 76, y - 54, 76 * 2, 54 - 16);
      d.shapes.triangle(x, y, x - 16, y - 16, x + 16, y - 16);
    }
    
    d.shapes.end();
    
    d.shapes.begin(ShapeRenderer.ShapeType.Line);
    
    d.shapes.setColor(Color.BLACK);
    
    if (flipped) {
      d.shapes.line(x, y, x - 16, y + 16);
      d.shapes.line(x, y, x + 16, y + 16);
      d.shapes.line(x - 16, y + 16, x - 76, y + 16);
      d.shapes.line(x - 76, y + 16, x - 76, y + 54);
      d.shapes.line(x - 76, y + 54, x + 76, y + 54);
      d.shapes.line(x + 76, y + 54, x + 76, y + 16);
      d.shapes.line(x + 76, y + 16, x + 16, y + 16);
    } else {
      d.shapes.line(x, y, x - 16, y - 16);
      d.shapes.line(x, y, x + 16, y - 16);
      d.shapes.line(x - 16, y - 16, x - 76, y - 16);
      d.shapes.line(x - 76, y - 16, x - 76, y - 54);
      d.shapes.line(x - 76, y - 54, x + 76, y - 54);
      d.shapes.line(x + 76, y - 54, x + 76, y - 16);
      d.shapes.line(x + 76, y - 16, x + 16, y - 16);
    }
    
    d.shapes.end();
    
    for (Button button : buttons) {
      button.draw(d);
    }
  }

  @Override
  public void press(Button button) {
    Class<? extends Piece> selected = null;
    
    if (button == queenButton) {
      selected = Queen.class;
    } else if (button == bishopButton) {
      selected = Bishop.class;
    } else if (button == rookButton) {
      selected = Rook.class;
    } else if (button == knightButton) {
      selected = Knight.class;
    }
    
    if (selected != null) {
      game.getGame().promotePawn(selected);
      game.pawnPromo = null;
    }
  }
}

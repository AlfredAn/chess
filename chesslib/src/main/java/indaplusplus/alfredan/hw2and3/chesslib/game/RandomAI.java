package indaplusplus.alfredan.hw2and3.chesslib.game;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Makes completely random moves.
 * If a pawn can be promoted, a queen is always chosen.
 */
public final class RandomAI extends StandardChessAI {
  
  private final Random random = new Random();
  
  @Override
  public void makeMove(StandardChessGame game) {
    Board board = game.getBoard();
    int myTeam = game.getTurn();
    
    List<List<IntVector2>> allMoves = new ArrayList<>();
    List<IntVector2> moveFrom = new ArrayList<>();
    
    for (int x = 0; x < board.getWidth(); x++) {
      for (int y = 0; y < board.getHeight(); y++) {
        Piece piece = board.get(x, y);
        if (piece != null && piece.team == myTeam) {
          List<IntVector2> moves = board.getAvailableMoves(x, y);
          
          if (!moves.isEmpty()) {
            allMoves.add(moves);
            moveFrom.add(new IntVector2(x, y));
          }
        }
      }
    }
    
    if (allMoves.isEmpty()) {
      return;
    }
    
    int pieceIndex = random.nextInt(allMoves.size());
    IntVector2 from = moveFrom.get(pieceIndex);
    
    List<IntVector2> moves = allMoves.get(pieceIndex);
    IntVector2 to = moves.get(random.nextInt(moves.size()));
    
    game.move(from.x, from.y, to.x, to.y);
    
    if (game.canPromotePawn()) {
      game.promotePawn(Queen.class);
    }
    
    if (game.canDeclareDraw()) {
      game.declareDraw();
    }
  }
}

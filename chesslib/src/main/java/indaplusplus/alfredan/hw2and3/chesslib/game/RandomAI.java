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
    
    int numberOfMoves = 0;
    for (int x = 0; x < board.getWidth(); x++) {
      for (int y = 0; y < board.getHeight(); y++) {
        Piece piece = board.get(x, y);
        if (piece != null && piece.team == myTeam) {
          List<IntVector2> moves = board.getAvailableMoves(x, y);
          allMoves.add(moves);
          numberOfMoves += moves.size();
          
          moveFrom.add(new IntVector2(x, y));
        }
      }
    }
    
    if (numberOfMoves == 0) {
      return;
    }
    
    int moveIndex = random.nextInt(numberOfMoves);
    for (int i = 0; i < allMoves.size(); i++) {
      List<IntVector2> moves = allMoves.get(i);
      
      if (moveIndex < moves.size()) {
        // make the move
        IntVector2 from = moveFrom.get(i);
        IntVector2 to = moves.get(moveIndex);
        
        game.move(from.x, from.y, to.x, to.y);
        
        break;
      } else {
        moveIndex -= moves.size();
      }
    }
    
    if (game.canPromotePawn()) {
      game.promotePawn(Queen.class);
    }
  }
}

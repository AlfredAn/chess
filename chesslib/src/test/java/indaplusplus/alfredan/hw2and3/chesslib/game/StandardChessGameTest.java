package indaplusplus.alfredan.hw2and3.chesslib.game;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.game.StandardChessGame.GameStatus;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Bishop;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.King;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Pawn;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Rook;
import static org.junit.Assert.*;
import org.junit.Test;

public class StandardChessGameTest {
  
  @Test
  public void testPawnPromotion() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Pawn pawn = new Pawn(Team.WHITE);
    Rook rook = new Rook(Team.BLACK);
    
    mBoard.set(1, 1, pawn);
    mBoard.set(6, 6, rook);
    
    StandardChessGame game = new StandardChessGame(new Board(mBoard));
    
    // the rook's moves are just for filler
    
    assertTrue(game.move(1, 1, 1, 3));
    assertTrue(game.move(6, 6, 6, 7));
    assertTrue(game.move(1, 3, 1, 4));
    assertTrue(game.move(6, 7, 6, 6));
    assertTrue(game.move(1, 4, 1, 5));
    assertTrue(game.move(6, 6, 6, 7));
    assertTrue(game.move(1, 5, 1, 6));
    assertTrue(game.move(6, 7, 6, 6));
    assertTrue(game.move(1, 6, 1, 7));
    
    assertTrue(game.canPromotePawn());
    game.promotePawn(Queen.class);
    
    Board board = game.getBoard();
    
    assertTrue(board.get(1, 7) instanceof Queen);
  }
  
  @Test
  public void testCheckmate() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    mBoard.set(5, 0, new Rook(Team.WHITE));
    mBoard.set(6, 0, new King(Team.WHITE));
    mBoard.set(7, 0, new Queen(Team.BLACK));
    mBoard.set(5, 1, new Pawn(Team.WHITE));
    mBoard.set(5, 2, new Pawn(Team.WHITE));
    mBoard.set(7, 4, new Rook(Team.BLACK));
    mBoard.set(4, 7, new King(Team.BLACK));
    
    StandardChessGame game = new StandardChessGame(new Board(mBoard));
    
    assertEquals(GameStatus.BLACK_WIN, game.getGameStatus());
  }
  
  @Test
  public void testStalemate() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    mBoard.set(2, 7, new King(Team.BLACK));
    mBoard.set(3, 5, new Queen(Team.WHITE));
    mBoard.set(4, 4, new Bishop(Team.WHITE));
    mBoard.set(4, 2, new King(Team.WHITE));
    
    StandardChessGame game = new StandardChessGame(new Board(mBoard));
    
    assertTrue(game.move(3, 5, 3, 4));
    
    assertEquals(GameStatus.DRAW, game.getGameStatus());
  }
  
  @Test
  public void testThreefoldRepetition() {
    StandardChessGame game = new StandardChessGame();
    
    // move the knights back and forth two times -
    // the initial position will now be reached for the third time
    for (int i = 0; i < 2; i++) {
      assertFalse(game.canDeclareDraw());
      assertTrue(game.move(1, 0, 2, 2));
      assertFalse(game.canDeclareDraw());
      assertTrue(game.move(1, 7, 2, 5));
      assertFalse(game.canDeclareDraw());
      assertTrue(game.move(2, 2, 1, 0));
      assertFalse(game.canDeclareDraw());
      assertTrue(game.move(2, 5, 1, 7));
    }
    
    assertTrue(game.canDeclareDraw());
    
    // make sure you can still move
    assertTrue(game.move(1, 0, 2, 2));
    assertTrue(game.move(1, 7, 2, 5));
    assertTrue(game.move(2, 2, 1, 0));
    assertTrue(game.move(2, 5, 1, 7));
    
    // make sure you can still declare draw after fourfold repetition
    assertTrue(game.canDeclareDraw());
  }
  
  @Test
  public void testFiftyMoveRule() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    mBoard.set(0, 1, new Rook(Team.WHITE));
    mBoard.set(0, 0, new Rook(Team.BLACK));
    
    StandardChessGame game = new StandardChessGame(new Board(mBoard));
    
    int rookX = 0, rookY = 0;
    // move the rooks space invaders style for fifty moves
    for (int i = 0, dir = 1; i < 50; i++) {
      assertFalse(game.canDeclareDraw());
      
      if ((dir == 1 && rookX == 7) || (dir == -1 && rookX == 0)) {
        dir = -dir;
        // go down one square to avoid the threefold repetition rule
        assertTrue(game.move(rookX, rookY + 1, rookX, rookY + 2));
        assertFalse(game.canDeclareDraw());
        assertTrue(game.move(rookX, rookY, rookX, rookY + 1));
        rookY++;
      } else {
        assertTrue(game.move(rookX, rookY + 1, rookX + dir, rookY + 1));
        assertFalse(game.canDeclareDraw());
        assertTrue(game.move(rookX, rookY, rookX + dir, rookY));
        rookX += dir;
      }
    }
    
    assertTrue(game.canDeclareDraw());
    
    // make sure you can still move
    assertTrue(game.move(rookX, rookY + 1, rookX, rookY));
    
    // make sure the rule still applies after an additional move
    assertTrue(game.canDeclareDraw());
  }
}

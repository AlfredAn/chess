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
import org.junit.Assert;
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
    
    Assert.assertTrue(game.move(1, 1, 1, 3));
    Assert.assertTrue(game.move(6, 6, 6, 7));
    Assert.assertTrue(game.move(1, 3, 1, 4));
    Assert.assertTrue(game.move(6, 7, 6, 6));
    Assert.assertTrue(game.move(1, 4, 1, 5));
    Assert.assertTrue(game.move(6, 6, 6, 7));
    Assert.assertTrue(game.move(1, 5, 1, 6));
    Assert.assertTrue(game.move(6, 7, 6, 6));
    Assert.assertTrue(game.move(1, 6, 1, 7));
    
    Assert.assertTrue(game.canPromotePawn());
    game.promotePawn(Queen.class);
    
    Board board = game.getBoard();
    
    Assert.assertTrue(board.get(1, 7) instanceof Queen);
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
    
    Assert.assertEquals(GameStatus.BLACK_WIN, game.getGameStatus());
  }
  
  @Test
  public void testStalemate() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    mBoard.set(2, 7, new King(Team.BLACK));
    mBoard.set(3, 5, new Queen(Team.WHITE));
    mBoard.set(4, 4, new Bishop(Team.WHITE));
    mBoard.set(4, 2, new King(Team.WHITE));
    
    StandardChessGame game = new StandardChessGame(new Board(mBoard));
    
    Assert.assertTrue(game.move(3, 5, 3, 4));
    
    Assert.assertEquals(GameStatus.DRAW, game.getGameStatus());
  }
}

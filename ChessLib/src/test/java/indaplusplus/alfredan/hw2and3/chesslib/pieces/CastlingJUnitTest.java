package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CastlingJUnitTest {
  
  @Test
  public void testCastleKingSide() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    
    mBoard.set(4, 0, king);
    mBoard.set(7, 0, rook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    Assert.assertEquals("moveList.size()", 6, moveList.size());
    Assert.assertTrue("contains (6, 0)", moveList.contains(new IntVector2(6, 0)));
    
    board = board.makeMove(4, 0, 6, 0);
    
    Assert.assertTrue("board.get(6, 0)", board.get(6, 0) instanceof King);
    Assert.assertTrue("board.get(5, 0)", board.get(5, 0) instanceof Rook);
    Assert.assertNull("board.get(4, 0)", board.get(4, 0));
    Assert.assertNull("board.get(7, 0)", board.get(7, 0));
  }
  
  @Test
  public void testCastleQueenSide() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    
    mBoard.set(4, 0, king);
    mBoard.set(0, 0, rook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    Assert.assertEquals("moveList.size()", 6, moveList.size());
    Assert.assertTrue("contains (2, 0)", moveList.contains(new IntVector2(2, 0)));
    
    board = board.makeMove(4, 0, 2, 0);
    
    Assert.assertTrue("board.get(6, 0)", board.get(2, 0) instanceof King);
    Assert.assertTrue("board.get(5, 0)", board.get(3, 0) instanceof Rook);
    Assert.assertNull("board.get(4, 0)", board.get(4, 0));
    Assert.assertNull("board.get(7, 0)", board.get(0, 0));
  }
  
  @Test
  public void testKingAlreadyMoved() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    
    mBoard.set(4, 0, king);
    mBoard.set(7, 0, rook);
    
    Board board = new Board(mBoard);
    
    board = board.makeMove(4, 0, 4, 1);
    board = board.makeMove(4, 1, 4, 0);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    Assert.assertEquals(5, moveList.size());
  }
  
  @Test
  public void testRookAlreadyMoved() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    
    mBoard.set(4, 0, king);
    mBoard.set(7, 0, rook);
    
    Board board = new Board(mBoard);
    
    board = board.makeMove(7, 0, 7, 4);
    board = board.makeMove(7, 4, 7, 0);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    Assert.assertEquals(5, moveList.size());
  }
}

package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PawnJUnitTest {
  
  @Test
  public void testWhiteFirstMove() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn = new Pawn(Team.WHITE);
    
    mBoard.set(1, 1, pawn);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 1);
    
    Assert.assertEquals("moveList.size()", 2, moveList.size());
    Assert.assertTrue("moveList contains (1, 2)", moveList.contains(new IntVector2(1, 2)));
    Assert.assertTrue("moveList contains (1, 3)", moveList.contains(new IntVector2(1, 3)));
  }
  
  @Test
  public void testBlackFirstMove() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn = new Pawn(Team.BLACK);
    
    mBoard.set(1, 6, pawn);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 6);
    
    Assert.assertEquals("moveList.size()", 2, moveList.size());
    Assert.assertTrue("moveList contains (1, 5)", moveList.contains(new IntVector2(1, 5)));
    Assert.assertTrue("moveList contains (1, 4)", moveList.contains(new IntVector2(1, 4)));
  }
  
  @Test
  public void testSecondMove() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn = new Pawn(Team.WHITE);
    
    mBoard.set(1, 1, pawn);
    
    Board board = new Board(mBoard);
    
    board = board.makeMove(1, 1, 1, 3);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 3);
    
    Assert.assertEquals("moveList.size()", 1, moveList.size());
    Assert.assertTrue("moveList contains (1, 4)", moveList.contains(new IntVector2(1, 4)));
  }
  
  @Test
  public void testCapture1() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    mBoard.set(1, 1, pawn1);
    mBoard.set(2, 2, pawn2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList1 = board.getAvailableMoves(1, 1);
    
    Assert.assertEquals("moveList1.size()", 3, moveList1.size());
    Assert.assertTrue("moveList1 contains (2, 2)", moveList1.contains(new IntVector2(2, 2))); // capture
    Assert.assertTrue("moveList1 contains (1, 2)", moveList1.contains(new IntVector2(1, 2))); // forward
    Assert.assertTrue("moveList1 contains (1, 3)", moveList1.contains(new IntVector2(1, 3))); // double forward
    
    List<IntVector2> moveList2 = board.getAvailableMoves(2, 2);
    
    Assert.assertEquals("moveList2.size()", 3, moveList2.size());
    Assert.assertTrue("moveList2 contains (1, 1)", moveList2.contains(new IntVector2(1, 1))); // capture
    Assert.assertTrue("moveList2 contains (2, 1)", moveList2.contains(new IntVector2(2, 1))); // forward
    Assert.assertTrue("moveList2 contains (2, 0)", moveList2.contains(new IntVector2(2, 0))); // double forward
  }
  
  @Test
  public void testCapture2() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    mBoard.set(2, 1, pawn1);
    mBoard.set(1, 2, pawn2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList1 = board.getAvailableMoves(2, 1);
    
    Assert.assertEquals("moveList1.size()", 3, moveList1.size());
    Assert.assertTrue("moveList1 contains (1, 2)", moveList1.contains(new IntVector2(1, 2))); // capture
    Assert.assertTrue("moveList1 contains (2, 2)", moveList1.contains(new IntVector2(2, 2))); // forward
    Assert.assertTrue("moveList1 contains (2, 3)", moveList1.contains(new IntVector2(2, 3))); // double forward
    
    List<IntVector2> moveList2 = board.getAvailableMoves(1, 2);
    
    Assert.assertEquals("moveList2.size()", 3, moveList2.size());
    Assert.assertTrue("moveList2 contains (2, 1)", moveList2.contains(new IntVector2(2, 1))); // capture
    Assert.assertTrue("moveList2 contains (1, 1)", moveList2.contains(new IntVector2(1, 1))); // forward
    Assert.assertTrue("moveList2 contains (1, 0)", moveList2.contains(new IntVector2(1, 0))); // double forward
  }
  
  @Test
  public void testEnPassant() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    mBoard.set(0, 1, pawn1);
    mBoard.set(1, 3, pawn2);
    
    Board board = new Board(mBoard);
    
    board = board.makeMove(0, 1, 0, 3);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 3);
    
    Assert.assertEquals("moveList.size()", 3, moveList.size());
    Assert.assertTrue("moveList contains (1, 2)", moveList.contains(new IntVector2(1, 2))); // forward
    Assert.assertTrue("moveList contains (1, 1)", moveList.contains(new IntVector2(1, 1))); // double forward
    Assert.assertTrue("moveList1 contains (0, 2)", moveList.contains(new IntVector2(0, 2))); // en passant
    
    board = board.makeMove(1, 3, 0, 2);
    
    Assert.assertTrue("board.get(0, 2) instanceof Pawn", board.get(0, 2) instanceof Pawn);
    Assert.assertNull("board.get(0, 3)", board.get(0, 3));
  }
  
  @Test
  public void testEnPassantTooLate() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    mBoard.set(0, 1, pawn1);
    mBoard.set(1, 4, pawn2);
    
    Board board = new Board(mBoard);
    
    board = board.makeMove(0, 1, 0, 3).makeMove(1, 4, 1, 3);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 3);
    
    Assert.assertEquals("moveList.size()", 1, moveList.size());
    Assert.assertTrue("moveList1 contains (1, 2)", moveList.contains(new IntVector2(1, 2))); // forward
  }
}

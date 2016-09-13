package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.ChessBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PawnJUnitTest {
  
  @Test
  public void testWhiteFirstMove() {
    ChessBoard board = new ChessBoard();
    Pawn pawn = new Pawn(Team.WHITE);
    
    board.placePiece(pawn, 1, 1);
    
    List<IntVector2> moveList = pawn.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 2, moveList.size());
    Assert.assertTrue("moveList contains (1, 2)", moveList.contains(new IntVector2(1, 2)));
    Assert.assertTrue("moveList contains (1, 3)", moveList.contains(new IntVector2(1, 3)));
  }
  
  @Test
  public void testBlackFirstMove() {
    ChessBoard board = new ChessBoard();
    Pawn pawn = new Pawn(Team.BLACK);
    
    board.placePiece(pawn, 1, 6);
    
    List<IntVector2> moveList = pawn.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 2, moveList.size());
    Assert.assertTrue("moveList contains (1, 5)", moveList.contains(new IntVector2(1, 5)));
    Assert.assertTrue("moveList contains (1, 4)", moveList.contains(new IntVector2(1, 4)));
  }
  
  @Test
  public void testSecondMove() {
    ChessBoard board = new ChessBoard();
    Pawn pawn = new Pawn(Team.WHITE);
    
    board.placePiece(pawn, 1, 1);
    pawn.makeMove(1, 3);
    
    List<IntVector2> moveList = pawn.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 1, moveList.size());
    Assert.assertTrue("moveList contains (1, 4)", moveList.contains(new IntVector2(1, 4)));
  }
  
  @Test
  public void testCapture1() {
    ChessBoard board = new ChessBoard();
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    board.placePiece(pawn1, 1, 1);
    board.placePiece(pawn2, 2, 2);
    
    List<IntVector2> moveList1 = pawn1.getAvailableMoves();
    
    Assert.assertEquals("moveList1.size()", 3, moveList1.size());
    Assert.assertTrue("moveList1 contains (2, 2)", moveList1.contains(new IntVector2(2, 2))); // capture
    Assert.assertTrue("moveList1 contains (1, 2)", moveList1.contains(new IntVector2(1, 2))); // forward
    Assert.assertTrue("moveList1 contains (1, 3)", moveList1.contains(new IntVector2(1, 3))); // double forward
    
    List<IntVector2> moveList2 = pawn2.getAvailableMoves();
    
    Assert.assertEquals("moveList2.size()", 3, moveList2.size());
    Assert.assertTrue("moveList2 contains (1, 1)", moveList2.contains(new IntVector2(1, 1))); // capture
    Assert.assertTrue("moveList2 contains (2, 1)", moveList2.contains(new IntVector2(2, 1))); // forward
    Assert.assertTrue("moveList2 contains (2, 0)", moveList2.contains(new IntVector2(2, 0))); // double forward
  }
  
  @Test
  public void testCapture2() {
    ChessBoard board = new ChessBoard();
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    board.placePiece(pawn1, 2, 1);
    board.placePiece(pawn2, 1, 2);
    
    List<IntVector2> moveList1 = pawn1.getAvailableMoves();
    
    Assert.assertEquals("moveList1.size()", 3, moveList1.size());
    Assert.assertTrue("moveList1 contains (1, 2)", moveList1.contains(new IntVector2(1, 2))); // capture
    Assert.assertTrue("moveList1 contains (2, 2)", moveList1.contains(new IntVector2(2, 2))); // forward
    Assert.assertTrue("moveList1 contains (2, 3)", moveList1.contains(new IntVector2(2, 3))); // double forward
    
    List<IntVector2> moveList2 = pawn2.getAvailableMoves();
    
    Assert.assertEquals("moveList2.size()", 3, moveList2.size());
    Assert.assertTrue("moveList2 contains (2, 1)", moveList2.contains(new IntVector2(2, 1))); // capture
    Assert.assertTrue("moveList2 contains (1, 1)", moveList2.contains(new IntVector2(1, 1))); // forward
    Assert.assertTrue("moveList2 contains (1, 0)", moveList2.contains(new IntVector2(1, 0))); // double forward
  }
  
  @Test
  public void testEnPassant() {
    ChessBoard board = new ChessBoard();
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    board.placePiece(pawn1, 0, 1);
    board.placePiece(pawn2, 1, 3);
    
    pawn1.makeMove(0, 3);
    
    List<IntVector2> moveList = pawn2.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 3, moveList.size());
    Assert.assertTrue("moveList contains (1, 2)", moveList.contains(new IntVector2(1, 2))); // forward
    Assert.assertTrue("moveList contains (1, 1)", moveList.contains(new IntVector2(1, 1))); // double forward
    Assert.assertTrue("moveList1 contains (0, 2)", moveList.contains(new IntVector2(0, 2))); // en passant
    
    pawn2.makeMove(0, 2);
    
    Assert.assertEquals("board.getPiece(0, 2)", pawn2, board.getPiece(0, 2));
    Assert.assertNull("board.getPiece(0, 3)", board.getPiece(0, 3));
    Assert.assertNull("pawn1.getBoard()", pawn1.getBoard());
  }
  
  @Test
  public void testEnPassantTooLate() {
    ChessBoard board = new ChessBoard();
    Pawn pawn1 = new Pawn(Team.WHITE);
    Pawn pawn2 = new Pawn(Team.BLACK);
    
    board.placePiece(pawn1, 0, 1);
    board.placePiece(pawn2, 1, 4);
    
    pawn1.makeMove(0, 3);
    pawn2.makeMove(1, 3);
    
    List<IntVector2> moveList = pawn2.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 1, moveList.size());
    Assert.assertTrue("moveList1 contains (1, 2)", moveList.contains(new IntVector2(1, 2))); // forward
  }
}

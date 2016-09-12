package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.ChessBoard;
import indaplusplus.alfredan.hw2and3.chesslib.ChessPiece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TemplatePieceJUnitTest {
  
  private static final class TestPiece extends TemplatePiece {
    
    private TestPiece(int team, MoveSet singleMoves, MoveSet repeatableMoves) {
      super(team, singleMoves, repeatableMoves);
    }
  }
  
  @Test
  public void testSingleMoves() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    board.placePiece(piece, 3, 4);
    
    List<IntVector2> moveList = piece.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 2, moveList.size());
    Assert.assertTrue("contains (3, 5)", moveList.contains(new IntVector2(3, 5)));
    Assert.assertTrue("contains (2, 4)", moveList.contains(new IntVector2(2, 4)));
  }
  
  @Test
  public void testSingleMovesOutOfBounds() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    board.placePiece(piece, 0, 0);
    
    List<IntVector2> moveList = piece.getAvailableMoves();
    
    Assert.assertEquals("moveList.size()", 1, moveList.size());
    Assert.assertTrue("contains (0, 1)", moveList.contains(new IntVector2(0, 1)));
  }
  
  @Test
  public void testSingleMoveOntoEnemy() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece1 = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    ChessPiece piece2 = new TestPiece(Team.WHITE, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    board.placePiece(piece1, 3, 4);
    board.placePiece(piece2, 3, 5);
    
    
    List<IntVector2> moveList1 = piece1.getAvailableMoves();
    
    Assert.assertEquals("moveList1.size()", 2, moveList1.size());
    Assert.assertTrue("1 contains (3, 5)", moveList1.contains(new IntVector2(3, 5)));
    Assert.assertTrue("1 contains (2, 4)", moveList1.contains(new IntVector2(2, 4)));
    
    
    List<IntVector2> moveList2 = piece2.getAvailableMoves();
    
    Assert.assertEquals("moveList2.size()", 2, moveList2.size());
    Assert.assertTrue("2 contains (3, 6)", moveList2.contains(new IntVector2(3, 6)));
    Assert.assertTrue("2 contains (2, 5)", moveList2.contains(new IntVector2(2, 5)));
  }
  
  @Test
  public void testSingleMoveOntoFriend() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece1 = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    ChessPiece piece2 = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    board.placePiece(piece1, 3, 4);
    board.placePiece(piece2, 3, 5);
    
    
    List<IntVector2> moveList1 = piece1.getAvailableMoves();
    
    Assert.assertEquals("moveList1.size()", 1, moveList1.size());
    Assert.assertTrue("1 contains (2, 4)", moveList1.contains(new IntVector2(2, 4)));
    
    
    List<IntVector2> moveList2 = piece2.getAvailableMoves();
    
    Assert.assertEquals("moveList2.size()", 2, moveList2.size());
    Assert.assertTrue("2 contains (3, 6)", moveList2.contains(new IntVector2(3, 6)));
    Assert.assertTrue("2 contains (2, 5)", moveList2.contains(new IntVector2(2, 5)));
  }
  
  @Test
  public void testRepeatableMove() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece(Team.BLACK, null, new MoveSet(new int[][] {{1, 1}, {1, -1}}));
    
    board.placePiece(piece, 1, 3);
    
    List<IntVector2> moveList = piece.getAvailableMoves();
    
    IntVector2[] referenceArray = new IntVector2[] {
      // (1, 1)
      new IntVector2(2, 4),
      new IntVector2(3, 5),
      new IntVector2(4, 6),
      new IntVector2(5, 7),
      
      // (1, -1)
      new IntVector2(2, 2),
      new IntVector2(3, 1),
      new IntVector2(4, 0)
    };
    
    Assert.assertEquals("moveList.size()", referenceArray.length, moveList.size());
    for (IntVector2 move : referenceArray) {
      Assert.assertTrue("moveList contains (" + move.x + ", " + move.y + ")", moveList.contains(move));
    }
  }
  
  @Test
  public void testRepeatableMoveOntoEnemy() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece1 = new TestPiece(Team.BLACK, null, new MoveSet(new int[][] {{1, 1}, {1, -1}}));
    ChessPiece piece2 = new TestPiece(Team.WHITE, null, null);
    
    board.placePiece(piece1, 1, 3);
    board.placePiece(piece2, 4, 6);
    
    List<IntVector2> moveList = piece1.getAvailableMoves();
    
    IntVector2[] referenceArray = new IntVector2[] {
      // (1, 1)
      new IntVector2(2, 4),
      new IntVector2(3, 5),
      new IntVector2(4, 6), //piece2 here, stop!
      
      // (1, -1)
      new IntVector2(2, 2),
      new IntVector2(3, 1),
      new IntVector2(4, 0)
    };
    
    Assert.assertEquals("moveList.size()", referenceArray.length, moveList.size());
    for (IntVector2 move : referenceArray) {
      Assert.assertTrue("moveList contains (" + move.x + ", " + move.y + ")", moveList.contains(move));
    }
  }
  
  @Test
  public void testRepeatableMoveOntoFriend() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece1 = new TestPiece(Team.BLACK, null, new MoveSet(new int[][] {{1, 1}, {1, -1}}));
    ChessPiece piece2 = new TestPiece(Team.BLACK, null, null);
    
    board.placePiece(piece1, 1, 3);
    board.placePiece(piece2, 4, 6);
    
    List<IntVector2> moveList = piece1.getAvailableMoves();
    
    IntVector2[] referenceArray = new IntVector2[] {
      // (1, 1)
      new IntVector2(2, 4),
      new IntVector2(3, 5),
      //piece2 here at (4, 6), stop!
      
      // (1, -1)
      new IntVector2(2, 2),
      new IntVector2(3, 1),
      new IntVector2(4, 0)
    };
    
    Assert.assertEquals("moveList.size()", referenceArray.length, moveList.size());
    for (IntVector2 move : referenceArray) {
      Assert.assertTrue("moveList contains (" + move.x + ", " + move.y + ")", moveList.contains(move));
    }
  }
}

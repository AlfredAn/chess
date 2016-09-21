package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Piece;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import indaplusplus.alfredan.hw2and3.chesslib.util.MoveSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TemplatePieceJUnitTest {
  
  private static final class TestPiece extends TemplatePiece {
    
    private final MoveSet singleMoves, repeatableMoves;
    
    private TestPiece(int team, MoveSet singleMoves, MoveSet repeatableMoves) {
      super(team);
      
      this.singleMoves = singleMoves == null ? MoveSet.EMPTY : singleMoves;
      this.repeatableMoves = repeatableMoves == null ? MoveSet.EMPTY : repeatableMoves;
    }
    
    @Override
    protected MoveSet getSingleMoveSet() {
      return singleMoves;
    }
    
    @Override
    protected MoveSet getRepeatableMoveSet() {
      return repeatableMoves;
    }
  }
  
  @Test
  public void testSingleMoves() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    mBoard.set(3, 4, piece);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(3, 4);
    
    Assert.assertEquals("moveList.size()", 2, moveList.size());
    Assert.assertTrue("contains (3, 5)", moveList.contains(new IntVector2(3, 5)));
    Assert.assertTrue("contains (2, 4)", moveList.contains(new IntVector2(2, 4)));
  }
  
  @Test
  public void testSingleMovesOutOfBounds() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    mBoard.set(0, 0, piece);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(0, 0);
    
    Assert.assertEquals("moveList.size()", 1, moveList.size());
    Assert.assertTrue("contains (0, 1)", moveList.contains(new IntVector2(0, 1)));
  }
  
  @Test
  public void testSingleMoveOntoEnemy() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece1 = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    Piece piece2 = new TestPiece(Team.WHITE, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    mBoard.set(3, 4, piece1);
    mBoard.set(3, 5, piece2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList1 = board.getAvailableMoves(3, 4);
    
    Assert.assertEquals("moveList1.size()", 2, moveList1.size());
    Assert.assertTrue("1 contains (3, 5)", moveList1.contains(new IntVector2(3, 5)));
    Assert.assertTrue("1 contains (2, 4)", moveList1.contains(new IntVector2(2, 4)));
    
    
    List<IntVector2> moveList2 = board.getAvailableMoves(3, 5);
    
    Assert.assertEquals("moveList2.size()", 2, moveList2.size());
    Assert.assertTrue("2 contains (3, 6)", moveList2.contains(new IntVector2(3, 6)));
    Assert.assertTrue("2 contains (2, 5)", moveList2.contains(new IntVector2(2, 5)));
  }
  
  @Test
  public void testSingleMoveOntoFriend() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece1 = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    Piece piece2 = new TestPiece(Team.BLACK, new MoveSet(new int[][] {{0, 1}, {-1, 0}}), null);
    
    mBoard.set(3, 4, piece1);
    mBoard.set(3, 5, piece2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList1 = board.getAvailableMoves(3, 4);
    
    Assert.assertEquals("moveList1.size()", 1, moveList1.size());
    Assert.assertTrue("1 contains (2, 4)", moveList1.contains(new IntVector2(2, 4)));
    
    
    List<IntVector2> moveList2 = board.getAvailableMoves(3, 5);
    
    Assert.assertEquals("moveList2.size()", 2, moveList2.size());
    Assert.assertTrue("2 contains (3, 6)", moveList2.contains(new IntVector2(3, 6)));
    Assert.assertTrue("2 contains (2, 5)", moveList2.contains(new IntVector2(2, 5)));
  }
  
  @Test
  public void testRepeatableMove() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece = new TestPiece(Team.BLACK, null, new MoveSet(new int[][] {{1, 1}, {1, -1}}));
    
    mBoard.set(1, 3, piece);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 3);
    
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
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece1 = new TestPiece(Team.BLACK, null, new MoveSet(new int[][] {{1, 1}, {1, -1}}));
    Piece piece2 = new TestPiece(Team.WHITE, null, null);
    
    mBoard.set(1, 3, piece1);
    mBoard.set(4, 6, piece2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 3);
    
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
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    Piece piece1 = new TestPiece(Team.BLACK, null, new MoveSet(new int[][] {{1, 1}, {1, -1}}));
    Piece piece2 = new TestPiece(Team.BLACK, null, null);
    
    mBoard.set(1, 3, piece1);
    mBoard.set(4, 6, piece2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(1, 3);
    
    IntVector2[] referenceArray = new IntVector2[] {
      // (1, 1)
      new IntVector2(2, 4),
      new IntVector2(3, 5),
      // piece2 here at (4, 6), stop!
      
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

package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.pieces.King;
import indaplusplus.alfredan.hw2and3.chesslib.pieces.Queen;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import indaplusplus.alfredan.hw2and3.chesslib.util.MutableBoardPool;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class BoardAndPieceJUnitTest {
  
  private static final class TestPiece extends Piece {
    
    private TestPiece() {
      super(Team.BLACK);
    }
    
    @Override
    protected List<IntVector2> getAvailableMoves(Board board, int xPos, int yPos) {
      List<IntVector2> moveList = new ArrayList<>(1);
      
      if (board.isValidPosition(xPos, yPos+1)) {
        moveList.add(new IntVector2(xPos, yPos+1));
      }
      
      return moveList;
    }
  }
  
  @Test
  public void testCreateBoardWithPiece() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    Piece piece = new TestPiece();
    
    mBoard.set(1, 1, piece);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    Assert.assertEquals("board.get(1, 1)", board.get(1, 1), piece);
  }
  
  @Test
  public void testMoveSetValidMove() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    Piece piece = new TestPiece();
    
    mBoard.set(1, 1, piece);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    List<IntVector2> moveSet = piece.getAvailableMoves(board, 1, 1);
    
    Assert.assertEquals("moveSet.size()", 1, moveSet.size());
    Assert.assertEquals("moveSet.get(0)", new IntVector2(1, 2), moveSet.get(0));
  }
  
  @Test
  public void testMoveSetInvalidMove() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    Piece piece = new TestPiece();
    
    mBoard.set(1, 7, piece);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    List<IntVector2> moveSet = piece.getAvailableMoves(board, 1, 7);
    
    Assert.assertEquals("moveSet.size()", 0, moveSet.size());
  }
  
  @Test
  public void testMovePiece() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    Piece piece = new TestPiece();
    
    mBoard.set(1, 1, piece);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    board = board.makeMove(1, 1, 1, 2);
    
    Assert.assertEquals("board.get(1, 2)", piece, board.get(1, 2));
    Assert.assertNull("board.get(1, 1)", board.get(1, 1));
  }
  
  @Test
  public void testCapturePiece() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    Piece piece1 = new TestPiece();
    Piece piece2 = new TestPiece();
    
    mBoard.set(1, 1, piece1);
    mBoard.set(1, 2, piece2);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    board = board.makeMove(1, 1, 1, 2);
    
    Assert.assertEquals("board.get(1, 2)", piece1, board.get(1, 2));
    Assert.assertNull("board.get(1, 1)", board.get(1, 1));
  }
  
  @Test
  public void testCheck() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    King king = new King(Team.BLACK);
    Queen queen = new Queen(Team.WHITE);
    
    mBoard.set(1, 1, king);
    mBoard.set(6, 6, queen);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    Assert.assertTrue(board.isChecked(Team.BLACK));
  }
  
  @Test
  public void testNoCheck() {
    MutableBoard mBoard = MutableBoardPool.obtain(8, 8);
    
    King king = new King(Team.BLACK);
    Queen queen = new Queen(Team.WHITE);
    
    mBoard.set(1, 1, king);
    mBoard.set(7, 6, queen);
    
    Board board = new Board(mBoard);
    MutableBoardPool.free(mBoard);
    
    Assert.assertFalse(board.isChecked(Team.BLACK));
  }
}

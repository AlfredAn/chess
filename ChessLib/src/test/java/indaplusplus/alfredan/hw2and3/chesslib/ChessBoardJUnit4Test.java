package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChessBoardJUnit4Test {
  
  public ChessBoardJUnit4Test() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }
  
  /**
   * Check that the default empty board is the correct size
   */
  @Test
  public void testCreateEmptyDefault() {
    ChessBoard board = new ChessBoard();
    
    Assert.assertEquals("Default board width",  8, board.width);
    Assert.assertEquals("Default board height", 8, board.height);
  }
  
  /**
   * Check that we can create a really big board of arbitrary size
   */
  @Test
  public void testCreateEmptyCustom() {
    ChessBoard board = new ChessBoard(234, 567);
    
    Assert.assertEquals("Board width",  234, board.width);
    Assert.assertEquals("Board height", 567, board.height);
  }
  
  /**
   * Check that we can't create an invalid board
   */
  @Test
  public void testCreateInvalidBoard() {
    Assert.assertFalse(canCreateBoard(0, 8));
    Assert.assertFalse(canCreateBoard(8, 0));
    Assert.assertFalse(canCreateBoard(0, 0));
    Assert.assertFalse(canCreateBoard(-1, -1));
    Assert.assertFalse(canCreateBoard(8, -1));
    Assert.assertFalse(canCreateBoard(-1, 8));
  }
  
  /**
   * Returns whether it is possible to create a board with the specified dimensions.
   */
  private boolean canCreateBoard(int width, int height) {
    try {
      ChessBoard board = new ChessBoard(width, height);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
  
  /**
   * Test chess piece that cannot move by itself
   */
  private static final class TestPiece extends ChessPiece {
    
    @Override
    public List<IntVector2> getAvailableMoves() {
      return new ArrayList<>(0);
    }
  }
  
  @Test
  public void testUnplacedChessPiece() {
    ChessPiece piece = new TestPiece();
    
    Assert.assertNull(piece.getBoard());
  }
  
  @Test
  public void testPlaceChessPiece() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece();
    
    board.placePiece(piece, 3, 4);
    
    Assert.assertEquals("piece.getBoard()", board, piece.getBoard());
    Assert.assertEquals("piece.getX()", 3, piece.getX());
    Assert.assertEquals("piece.getY()", 4, piece.getY());
    Assert.assertEquals("board.getPiece(3, 4)", piece, board.getPiece(3, 4));
  }
  
  @Test
  public void testPlacePieceAtInvalidPosition() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece();
    
    try
    {
      board.placePiece(piece, 8, 8);
    } catch (IllegalArgumentException e) {
      return; // test succeeded
    }
    
    Assert.fail();
  }
  
  @Test
  public void testPlacePieceAtInvalidPosition2() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece();
    
    try
    {
      board.placePiece(piece, -1, -1);
    } catch (IllegalArgumentException e) {
      return; // test succeeded
    }
    
    Assert.fail();
  }
  
  @Test
  public void testMoveChessPiece() {
    ChessBoard board = new ChessBoard(10, 10);
    ChessPiece piece = new TestPiece();
    
    board.placePiece(piece, 3, 4);
    board.placePiece(piece, 9, 9);
    
    Assert.assertEquals("piece.getBoard()", board, piece.getBoard());
    Assert.assertEquals("piece.getX()", 9, piece.getX());
    Assert.assertEquals("piece.getY()", 9, piece.getY());
    Assert.assertEquals("board.getPiece(9, 9)", piece, board.getPiece(9, 9));
    Assert.assertNull("board.getPiece(3, 4)", board.getPiece(3, 4));
  }
  
  @Test
  public void testMoveChessPieceToSamePosition() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece();
    
    board.placePiece(piece, 3, 4);
    board.placePiece(piece, 3, 4);
    
    Assert.assertEquals("piece.getBoard()", board, piece.getBoard());
    Assert.assertEquals("piece.getX()", 3, piece.getX());
    Assert.assertEquals("piece.getY()", 4, piece.getY());
    Assert.assertEquals("board.getPiece(3, 4)", piece, board.getPiece(3, 4));
  }
  
  @Test
  public void testMoveChessPieceBetweenBoards() {
    ChessBoard board1 = new ChessBoard(1, 1);
    ChessBoard board2 = new ChessBoard(4, 4);
    ChessPiece piece = new TestPiece();
    
    board1.placePiece(piece, 0, 0);
    board2.placePiece(piece, 2, 3);
    
    Assert.assertEquals("piece.getBoard()", board2, piece.getBoard());
    Assert.assertEquals("piece.getX()", 2, piece.getX());
    Assert.assertEquals("piece.getY()", 3, piece.getY());
    Assert.assertEquals("board2.getPiece(2, 3)", piece, board2.getPiece(2, 3));
    Assert.assertNull("board1.getPiece(0, 0)", board1.getPiece(0, 0));
  }
  
  @Test
  public void testRemoveChessPiece() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece = new TestPiece();
    
    board.placePiece(piece, 3, 4);
    board.removePiece(piece);
    
    Assert.assertNull("piece.getBoard()", piece.getBoard());
    Assert.assertNull("board.getPiece(3, 4)", board.getPiece(3, 4));
  }
  
  @Test
  public void testPlaceTwoPieces() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece1 = new TestPiece();
    ChessPiece piece2 = new TestPiece();
    
    board.placePiece(piece1, 3, 4);
    board.placePiece(piece2, 4, 5);
    
    Assert.assertEquals("piece1.getBoard()", board, piece1.getBoard());
    Assert.assertEquals("piece1.getX()", 3, piece1.getX());
    Assert.assertEquals("piece1.getY()", 4, piece1.getY());
    Assert.assertEquals("board.getPiece(3, 4)", piece1, board.getPiece(3, 4));
    
    Assert.assertEquals("piece2.getBoard()", board, piece2.getBoard());
    Assert.assertEquals("piece2.getX()", 4, piece2.getX());
    Assert.assertEquals("piece2.getY()", 5, piece2.getY());
    Assert.assertEquals("board.getPiece(4, 5)", piece2, board.getPiece(4, 5));
  }
  
  @Test
  public void testCapturePiece() {
    ChessBoard board = new ChessBoard();
    ChessPiece piece1 = new TestPiece();
    ChessPiece piece2 = new TestPiece();
    
    board.placePiece(piece1, 3, 4);
    board.placePiece(piece2, 4, 5);
    board.placePiece(piece1, 4, 5);
    
    Assert.assertEquals("piece1.getBoard()", board, piece1.getBoard());
    Assert.assertEquals("piece1.getX()", 4, piece1.getX());
    Assert.assertEquals("piece1.getY()", 5, piece1.getY());
    Assert.assertEquals("board.getPiece(4, 5)", piece1, board.getPiece(4, 5));
    Assert.assertNull("piece2.getBoard()", piece2.getBoard());
    Assert.assertNull("board.getPiece(3, 4)", board.getPiece(3, 4));
  }
}

package indaplusplus.alfredan.hw2and3;

import indaplusplus.alfredan.hw2and3.chesslib.ChessBoard;
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
    
    Assert.assertEquals("Incorrect default board width",  8, board.width);
    Assert.assertEquals("Incorrect default board height", 8, board.height);
  }
  
  /**
   * Check that we can create a really big board of arbitrary size
   */
  @Test
  public void testCreateEmptyCustom() {
    ChessBoard board = new ChessBoard(234, 567);
    
    Assert.assertEquals("Incorrect board width",  234, board.width);
    Assert.assertEquals("Incorrect board height", 567, board.height);
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
}

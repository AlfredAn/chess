package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;
import static org.junit.Assert.*;
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
    
    assertEquals("moveList.size()", 6, moveList.size());
    assertTrue("contains (6, 0)", moveList.contains(new IntVector2(6, 0)));
    
    board = board.makeMove(4, 0, 6, 0);
    
    assertTrue("board.get(6, 0)", board.get(6, 0) instanceof King);
    assertTrue("board.get(5, 0)", board.get(5, 0) instanceof Rook);
    assertNull("board.get(4, 0)", board.get(4, 0));
    assertNull("board.get(7, 0)", board.get(7, 0));
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
    
    assertEquals("moveList.size()", 6, moveList.size());
    assertTrue("contains (2, 0)", moveList.contains(new IntVector2(2, 0)));
    
    board = board.makeMove(4, 0, 2, 0);
    
    assertTrue("board.get(6, 0)", board.get(2, 0) instanceof King);
    assertTrue("board.get(5, 0)", board.get(3, 0) instanceof Rook);
    assertNull("board.get(4, 0)", board.get(4, 0));
    assertNull("board.get(7, 0)", board.get(0, 0));
  }
  
  @Test
  public void testCantCastleRightIfChecked1() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    Rook enemyRook = new Rook(Team.BLACK);
    
    mBoard.set(4, 0, king);
    mBoard.set(7, 0, rook);
    mBoard.set(4, 7, enemyRook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    assertFalse(moveList.contains(new IntVector2(6, 0)));
  }
  
  @Test
  public void testCantCastleRightIfChecked2() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    Rook enemyRook = new Rook(Team.BLACK);
    
    mBoard.set(4, 0, king);
    mBoard.set(7, 0, rook);
    mBoard.set(5, 7, enemyRook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    assertFalse(moveList.contains(new IntVector2(6, 0)));
  }
  
  @Test
  public void testCantCastleRightIfChecked3() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    Rook enemyRook = new Rook(Team.BLACK);
    
    mBoard.set(4, 0, king);
    mBoard.set(7, 0, rook);
    mBoard.set(6, 7, enemyRook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    assertFalse(moveList.contains(new IntVector2(6, 0)));
  }
  
  @Test
  public void testCantCastleLeftIfChecked1() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    Rook enemyRook = new Rook(Team.BLACK);
    
    mBoard.set(4, 0, king);
    mBoard.set(0, 0, rook);
    mBoard.set(4, 7, enemyRook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    assertFalse(moveList.contains(new IntVector2(2, 0)));
  }
  
  @Test
  public void testCantCastleLeftIfChecked2() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    Rook enemyRook = new Rook(Team.BLACK);
    
    mBoard.set(4, 0, king);
    mBoard.set(0, 0, rook);
    mBoard.set(3, 7, enemyRook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    assertFalse(moveList.contains(new IntVector2(2, 0)));
  }
  
  @Test
  public void testCantCastleLeftIfChecked3() {
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    King king = new King(Team.WHITE);
    Rook rook = new Rook(Team.WHITE);
    Rook enemyRook = new Rook(Team.BLACK);
    
    mBoard.set(4, 0, king);
    mBoard.set(0, 0, rook);
    mBoard.set(2, 7, enemyRook);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList = board.getAvailableMoves(4, 0);
    
    assertFalse(moveList.contains(new IntVector2(2, 0)));
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
    
    assertEquals(5, moveList.size());
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
    
    assertEquals(5, moveList.size());
  }
}

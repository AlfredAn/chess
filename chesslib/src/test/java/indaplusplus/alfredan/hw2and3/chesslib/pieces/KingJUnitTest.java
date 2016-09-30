package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.Board;
import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import indaplusplus.alfredan.hw2and3.chesslib.Team;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class KingJUnitTest {
  
  @Test
  public void testKingCanMoveIntoRangeOfEnemyKing() {
    King king1 = new King(Team.BLACK);
    King king2 = new King(Team.WHITE);
    
    MutableBoard mBoard = new MutableBoard(8, 8);
    
    mBoard.set(4, 4, king1);
    mBoard.set(4, 6, king2);
    
    Board board = new Board(mBoard);
    
    List<IntVector2> moveList1 = board.getAvailableMoves(4, 4);
    List<IntVector2> moveList2 = board.getAvailableMoves(4, 6);
    
    assertFalse(moveList1.contains(new IntVector2(4, 5)));
    assertFalse(moveList2.contains(new IntVector2(4, 5)));
  }
}

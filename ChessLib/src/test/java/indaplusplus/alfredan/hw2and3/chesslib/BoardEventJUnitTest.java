package indaplusplus.alfredan.hw2and3.chesslib;

import indaplusplus.alfredan.hw2and3.chesslib.pieces.Pawn;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class BoardEventJUnitTest {
  
  private static final class TestListener implements BoardEventListener {
    
    private List<BoardEvent> events = new ArrayList<>();
    
    @Override
    public void boardEvent(BoardEvent event) {
      events.add(event);
    }
  }
  
  @Test
  public void testEndOfTurnEvent() {
    ChessBoard board = new ChessBoard();
    TestListener listener = new TestListener();
    Pawn pawn = new Pawn(Team.WHITE);
    
    board.addEventListener(listener);
    
    board.placePiece(pawn, 1, 1);
    pawn.makeMove(1, 2);
    
    Assert.assertEquals("events.size()", 1, listener.events.size());
    Assert.assertTrue("events.get(0) instanceof BoardEvent.EndOfTurnEvent", listener.events.get(0) instanceof BoardEvent.EndOfTurnEvent);
  }
}

package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.pieces.MoveSet;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class MoveSetJUnitTest {
  
  @Test
  public void testEmptyMoveSet() {
    MoveSet ms = new MoveSet(new IntVector2[] {});
    
    Assert.assertEquals("ms.size()", 0, ms.size());
  }
  
  @Test
  public void testGetElementsFromMoveSet() {
    MoveSet ms = new MoveSet(new IntVector2[] {new IntVector2(1, 2), new IntVector2(2, 1)});
    
    Assert.assertEquals("ms.size()", 2, ms.size());
    Assert.assertEquals("ms.get(0)", new IntVector2(1, 2), ms.get(0));
    Assert.assertEquals("ms.get(1)", new IntVector2(2, 1), ms.get(1));
  }
  
  @Test
  public void testConvenienceConstructor() {
    MoveSet ms = new MoveSet(new int[][] {
      {1, 2},
      {2, 1}});
    MoveSet reference = new MoveSet(new IntVector2[] {new IntVector2(1, 2), new IntVector2(2, 1)});
    
    Assert.assertEquals(reference, ms);
  }
  
  @Test
  public void testIterator() {
    MoveSet ms = new MoveSet(new IntVector2[] {new IntVector2(1, 2), new IntVector2(2, 1)});
    
    Iterator<IntVector2> iter = ms.iterator();
    
    Assert.assertTrue("iter.hasNext() #1", iter.hasNext());
    Assert.assertEquals("iter.next()", new IntVector2(1, 2), iter.next());
    
    Assert.assertTrue("iter.hasNext() #2", iter.hasNext());
    Assert.assertEquals("iter.next()", new IntVector2(2, 1), iter.next());
    
    Assert.assertFalse("iter.hasNext() #3", iter.hasNext());
  }
}

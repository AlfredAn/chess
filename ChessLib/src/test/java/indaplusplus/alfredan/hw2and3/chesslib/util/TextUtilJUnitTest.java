package indaplusplus.alfredan.hw2and3.chesslib.util;

import org.junit.Assert;
import org.junit.Test;

public class TextUtilJUnitTest {
  
  @Test
  public void testNormalBoard() {
    Assert.assertEquals("a1", TextUtil.getSquareText(0, 0, 8, 8));
    Assert.assertEquals("h8", TextUtil.getSquareText(7, 7, 8, 8));
    Assert.assertEquals("d5", TextUtil.getSquareText(3, 4, 8, 8));
  }
  
  @Test
  public void testBigBoard() {
    Assert.assertEquals("aa001", TextUtil.getSquareText(0, 0, 100, 100));
    Assert.assertEquals("ah008", TextUtil.getSquareText(7, 7, 100, 100));
    Assert.assertEquals("ad005", TextUtil.getSquareText(3, 4, 100, 100));
    Assert.assertEquals("cy027", TextUtil.getSquareText(76, 26, 100, 100));
    Assert.assertEquals("dv100", TextUtil.getSquareText(99, 99, 100, 100));
    Assert.assertEquals("bh055", TextUtil.getSquareText(33, 54, 100, 100));
  }
}

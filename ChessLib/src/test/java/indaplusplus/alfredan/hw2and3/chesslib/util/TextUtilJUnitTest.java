package indaplusplus.alfredan.hw2and3.chesslib.util;

import org.junit.Assert;
import org.junit.Test;

public class TextUtilJUnitTest {
  
  @Test
  public void testEncodeNormalBoard() {
    Assert.assertEquals("a1", TextUtil.getSquareText(0, 0, 8, 8));
    Assert.assertEquals("h8", TextUtil.getSquareText(7, 7, 8, 8));
    Assert.assertEquals("d5", TextUtil.getSquareText(3, 4, 8, 8));
  }
  
  @Test
  public void testEncodeBigBoard() {
    Assert.assertEquals("aa001", TextUtil.getSquareText(0, 0, 100, 100));
    Assert.assertEquals("ah008", TextUtil.getSquareText(7, 7, 100, 100));
    Assert.assertEquals("ad005", TextUtil.getSquareText(3, 4, 100, 100));
    Assert.assertEquals("cy027", TextUtil.getSquareText(76, 26, 100, 100));
    Assert.assertEquals("dv100", TextUtil.getSquareText(99, 99, 100, 100));
    Assert.assertEquals("bh055", TextUtil.getSquareText(33, 54, 100, 100));
  }
  
  @Test
  public void testDecodeNormalBoard() {
    Assert.assertEquals(new IntVector2(0, 0), TextUtil.readSquareText("a1"));
    Assert.assertEquals(new IntVector2(7, 7), TextUtil.readSquareText("h8"));
    Assert.assertEquals(new IntVector2(3, 4), TextUtil.readSquareText("d5"));
  }
  
  @Test
  public void testDecodeBigBoard() {
    Assert.assertEquals(new IntVector2(0, 0), TextUtil.readSquareText("aa001"));
    Assert.assertEquals(new IntVector2(7, 7), TextUtil.readSquareText("ah008"));
    Assert.assertEquals(new IntVector2(3, 4), TextUtil.readSquareText("ad005"));
    Assert.assertEquals(new IntVector2(76, 26), TextUtil.readSquareText("cy027"));
    Assert.assertEquals(new IntVector2(99, 99), TextUtil.readSquareText("dv100"));
    Assert.assertEquals(new IntVector2(33, 54), TextUtil.readSquareText("bh055"));
  }
}

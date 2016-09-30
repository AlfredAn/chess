package indaplusplus.alfredan.hw2and3.chesslib.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class TextUtilJUnitTest {
  
  @Test
  public void testEncodeNormalBoard() {
    assertEquals("a1", TextUtil.getSquareText(0, 0, 8, 8));
    assertEquals("h8", TextUtil.getSquareText(7, 7, 8, 8));
    assertEquals("d5", TextUtil.getSquareText(3, 4, 8, 8));
  }
  
  @Test
  public void testEncodeBigBoard() {
    assertEquals("aa001", TextUtil.getSquareText(0, 0, 100, 100));
    assertEquals("ah008", TextUtil.getSquareText(7, 7, 100, 100));
    assertEquals("ad005", TextUtil.getSquareText(3, 4, 100, 100));
    assertEquals("cy027", TextUtil.getSquareText(76, 26, 100, 100));
    assertEquals("dv100", TextUtil.getSquareText(99, 99, 100, 100));
    assertEquals("bh055", TextUtil.getSquareText(33, 54, 100, 100));
  }
  
  @Test
  public void testDecodeNormalBoard() {
    assertEquals(new IntVector2(0, 0), TextUtil.readSquareText("a1"));
    assertEquals(new IntVector2(7, 7), TextUtil.readSquareText("h8"));
    assertEquals(new IntVector2(3, 4), TextUtil.readSquareText("d5"));
  }
  
  @Test
  public void testDecodeBigBoard() {
    assertEquals(new IntVector2(0, 0), TextUtil.readSquareText("aa001"));
    assertEquals(new IntVector2(7, 7), TextUtil.readSquareText("ah008"));
    assertEquals(new IntVector2(3, 4), TextUtil.readSquareText("ad005"));
    assertEquals(new IntVector2(76, 26), TextUtil.readSquareText("cy027"));
    assertEquals(new IntVector2(99, 99), TextUtil.readSquareText("dv100"));
    assertEquals(new IntVector2(33, 54), TextUtil.readSquareText("bh055"));
  }
}

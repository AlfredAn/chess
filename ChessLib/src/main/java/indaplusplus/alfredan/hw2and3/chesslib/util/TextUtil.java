package indaplusplus.alfredan.hw2and3.chesslib.util;

public final class TextUtil {
  
  private TextUtil() {}
  
  /**
   * Returns the label for the specified square on a board of the specified dimensions.
   */
  public static String getSquareText(int x, int y, int boardWidth, int boardHeight) {
    String xText = formatNumber(x, boardWidth, 26, 'a');
    String yText = formatNumber(y + 1, boardHeight + 1, 10, '0'); // y + 1 since we start at one
    
    return xText + yText;
  }
  
  /**
   * <p>Formats the number to be used in square labels.
   * <p>Example: formatNumber(33, 100, 26, 'a') -> "ah"
   * <p>Some leading zeroes will be kept to ensure that the string always has the same
   * length for the same max and radix values.
   * 
   * @param n The number to format.
   * @param max The maximum number (exclusive) that n can reach.
   * @param radix The radix - use 10 for decimal.
   * @param firstChar The character to be used for zero.
   * <p>For subsequent numbers, the character will be:
   * {@code (char)(firstChar + units)}.
   * @return The formatted number as a String.
   * @throws IllegalArgumentException if n &lt; 0, n &gt;= max or radix &lt;= 0
   */
  public static final String formatNumber(int n, int max, int radix, char firstChar) {
    if (n < 0 || n >= max || radix <= 0) {
      throw new IllegalArgumentException();
    }
    
    if (max < radix) {
      return Character.toString((char)(firstChar + n));
    }
    
    int numChars = (int)Math.ceil(Math.log(max) / Math.log(radix)); // number of characters in the final String
    
    // convert to the specified radix, and keep any leading zeroes
    char[] buf = new char[numChars]; // won't ever need more than 7 chars
    int unit = 1;
    for (int i = 0; i < buf.length; i++) {
      int units = (n / unit) % radix;
      buf[buf.length-1-i] = (char)(firstChar + units);
      unit *= radix;
      
      if (units > 0) {
        numChars++;
      }
    }
    
    return String.valueOf(buf);
  }
}

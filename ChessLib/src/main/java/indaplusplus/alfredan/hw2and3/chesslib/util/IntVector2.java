package indaplusplus.alfredan.hw2and3.chesslib.util;

/**
 * An immutable class that contains two integers.
 * Used to represent positions and moves on the chess board.
 */
public final class IntVector2 {
  
  public final int x, y;
  
  public IntVector2(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public boolean equals(int x, int y) {
    return this.x == x && this.y == y;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IntVector2)) return false;
    
    IntVector2 other = (IntVector2)o;
    
    return other.x == x && other.y == y;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + x;
    hash = 41 * hash + y;
    return hash;
  }
  
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}

package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Defines a set of vectors that represent directions a piece can move in.
 * A MoveSet instance is immutable and is intended to be shared by all pieces of the same type.
 */
public final class MoveSet implements Iterable<IntVector2> {
  
  /**
   * An empty MoveSet. This is useful if you don't want a piece to be able to use a certain type of move.
   */
  public static final MoveSet EMPTY = new MoveSet(new IntVector2[] {});
  
  private final IntVector2[] moveSet;
  
  /**
   * Create a new MoveSet from an array of movement vectors.
   */
  public MoveSet(IntVector2[] moveSet) {
    this.moveSet = Arrays.copyOf(moveSet, moveSet.length); // copy for safety
  }
  
  /**
   * Create a new MoveSet from a list of movement vectors.
   */
  public MoveSet(List<IntVector2> moveSet) {
    this.moveSet = moveSet.toArray(new IntVector2[] {});
  }
  
  /**
   * Create a new MoveSet from an array of movement vectors.
   * Every sub-array has to be of length exactly two.
   * This is a convenience method intended to allow you to create a MoveSet as follows:
   * {@code MoveSet moveSet = new MoveSet(new int[][] {
   *     { 1,  2}, { 2,  1},
   *     { 2, -1}, { 1, -2},
   *     {-1, -2}, {-2, -1},
   *     {-2,  1}, {-1,  2}});}
   * (this example is from the knight)
   * 
   * @throws NullPointerException if the array or any of its sub-arrays is null
   * @throws IllegalArgumentException if any of the sub-arrays has a length not equal to 2
   */
  public MoveSet(int[]... moveSet) {
    this.moveSet = new IntVector2[moveSet.length];
    
    for (int i = 0; i < moveSet.length; i++) {
      if (moveSet[i].length != 2) {
        throw new IllegalArgumentException("Every movement vector needs to contain exactly two elements.");
      }
      
      this.moveSet[i] = new IntVector2(moveSet[i][0], moveSet[i][1]);
    }
  }
  
  /**
   * Returns the number of movement vectors in this MoveSet.
   */
  public int size() {
    return moveSet.length;
  }
  
  /**
   * Returns the movement vector with index i in this MoveSet.
   * 
   * @throws ArrayIndexOutOfBoundsException if i is not a valid index
   */
  public IntVector2 get(int i) {
    return moveSet[i];
  }
  
  /**
   * Returns an iterator over all the movement vectors in this MoveSet.
   */
  @Override
  public Iterator<IntVector2> iterator() {
    return new Iterator<IntVector2>() {
      
      private int i = 0;
      
      @Override
      public boolean hasNext() {
        return i < size();
      }
      
      @Override
      public IntVector2 next() {
        return get(i++);
      }
      
      @Override
      public void remove() {
        throw new UnsupportedOperationException("remove() is not supported by this iterator");
      }
    };
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MoveSet)) return false;
    
    MoveSet other = (MoveSet)o;
    
    // checking length first to potentially avoid having to compare the elements
    return size() == other.size() && Arrays.equals(moveSet, other.moveSet);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(moveSet);
  }
}

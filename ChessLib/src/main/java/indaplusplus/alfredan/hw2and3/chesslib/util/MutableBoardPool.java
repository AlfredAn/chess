package indaplusplus.alfredan.hw2and3.chesslib.util;

import indaplusplus.alfredan.hw2and3.chesslib.MutableBoard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that allows you to get temporary MutableBoard instances and later discard them
 * without having to constantly allocate objects.
 */
public final class MutableBoardPool {
  
  private static final Map<IntVector2, MutableBoardPool> poolMap = new HashMap<>();
  
  private static MutableBoardPool cachedPool;
  
  private static MutableBoardPool getPool(int width, int height) {
    if (cachedPool != null && cachedPool.width == width && cachedPool.height == height) {
      return cachedPool;
    }
    
    IntVector2 dimensions = new IntVector2(width, height);
    
    MutableBoardPool pool = poolMap.get(dimensions);
    
    if (pool == null) {
      pool = new MutableBoardPool(width, height, 256);
      poolMap.put(dimensions, pool);
    }
    
    cachedPool = pool;
    
    return pool;
  }
  
  /**
   * Returns a MutableBoard with the specified dimensions.
   */
  public static MutableBoard obtain(int width, int height) {
    return getPool(width, height).obtainBoard();
  }
  
  /**
   * Puts the MutableBoard back in the pool. Call this after you no longer need it.
   */
  public static void free(MutableBoard board) {
    getPool(board.getWidth(), board.getHeight()).freeBoard(board);
  }
  
  private final int width, height;
  private final int max;
  
  private final List<MutableBoard> pool = new ArrayList<>();
  
  private MutableBoardPool(int width, int height, int max) {
    this.width = width;
    this.height = height;
    
    this.max = max;
  }
  
  private MutableBoard obtainBoard() {
    if (pool.isEmpty()) {
      return new MutableBoard(width, height);
    } else {
      return pool.remove(pool.size()-1);
    }
  }
  
  private void freeBoard(MutableBoard board) {
    if (pool.size() < max) {
      pool.add(board);
    }
  }
}

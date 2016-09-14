package indaplusplus.alfredan.hw2and3.chesslib.pieces;

import indaplusplus.alfredan.hw2and3.chesslib.ChessPiece;
import indaplusplus.alfredan.hw2and3.chesslib.util.IntVector2;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utilities to make it easier to make many common types of chess pieces.
 * All built-in chess pieces extend this class,
 * but you are also free to extend ChessPiece directly if you so desire.
 */
public abstract class TemplatePiece extends ChessPiece {
  
  /**
   * This MoveSet defines a set of moves that can only be performed once per turn.
   * In standard chess, the pawn, the knight and the king all move this way,
   * although the pawn has some special cases.
   */
  protected final MoveSet singleMoves;
  
  /**
   * This MoveSet defines a set of moves that can be repeated until you reach the edge
   * of the board or meet another piece.
   * In standard chess, the queen, the rook and the bishop all move this way.
   */
  protected final MoveSet repeatableMoves;
  
  /**
   * Creates a TemplatePiece with the specified MoveSets.
   * If either of the parameters are null, they will be replaced by MoveSet.EMPTY.
   * 
   * @param team The team that this piece belongs to
   * @param singleMoves The set of moves that can be performed only once per turn.
   * @param repeatableMoves The set of moves that can be repeated until you hit something.
   */
  public TemplatePiece(int team, MoveSet singleMoves, MoveSet repeatableMoves) {
    super(team);
    
    this.singleMoves = singleMoves == null ? MoveSet.EMPTY : singleMoves;
    this.repeatableMoves = repeatableMoves == null ? MoveSet.EMPTY : repeatableMoves;
  }
  
  @Override
  public final List<IntVector2> getAvailableMoves() {
    List<IntVector2> moveList = new ArrayList<>();
    
    addCustomMoves(moveList);
    
    // add all possible single moves
    for (int i = 0; i < singleMoves.size(); i++) {
      IntVector2 delta = singleMoves.get(i);
      
      int moveX = getX() + delta.x;
      int moveY = getY() + delta.y;
      
      // check if the move would lead to a valid position
      if (getBoard().isValidPosition(moveX, moveY)
              && canMoveSingle(delta, moveX, moveY, i)) {
        
        IntVector2 move = new IntVector2(moveX, moveY);
        
        // suppress duplicate moves
        if (!moveList.contains(move)) {
          moveList.add(move);
        }
      }
    }
    
    // add all possible repeatable moves
    for (int i = 0; i < repeatableMoves.size(); i++) {
      IntVector2 delta = repeatableMoves.get(i);
      
      int moveX = getX();
      int moveY = getY();
      
      int iteration = 0; // used to allow custom behavior based on distance moved
      boolean shouldStop = false; // whether the loop should stop after the current iteration
      
      // repeat until hitting another piece or the boundary
      outer: while (!shouldStop) {
        iteration++;
        
        moveX += delta.x;
        moveY += delta.y;
        
        // check if the move would lead to a valid position
        if (getBoard().isValidPosition(moveX, moveY)) {
          switch (canMoveRepeatable(delta, moveX, moveY, i, iteration)) {
            case INVALID_STOP:
              break outer;
            case INVALID_CONTINUE:
              continue;
            case VALID_STOP:
              shouldStop = true;
              break;
            case VALID_CONTINUE:
              break;
            default: // cannot happen, all cases are covered
              throw new AssertionError();
          }
          
          IntVector2 move = new IntVector2(moveX, moveY);
          
          // suppress duplicate moves
          if (!moveList.contains(move)) {
            moveList.add(move);
          }
        } else {
          // invalid move - stop iterating
          break;
        }
      }
    }
    
    return moveList;
  }
  
  /**
   * <p>Returns whether the piece can move to the specified position using a single move.
   * <p>This is intended to be overridden by subclasses for custom behavior.
   * <p>The position given by moveX and moveY is guaranteed to be within bounds.
   * <p>By default, this method returns false only if the destination square contains
   * a friendly piece.
   * 
   * @param delta The offset from the piece's current position
   * @param moveX The x position after making the move
   * @param moveY The y position after making the move
   * @param index The index of this move in the MoveSet
   * @return Whether the move is valid
   */
  protected boolean canMoveSingle(IntVector2 delta, int moveX, int moveY, int index) {
    ChessPiece pieceAtDestination = getBoard().getPiece(moveX, moveY);
    
    return pieceAtDestination == null || pieceAtDestination.team != team;
  }
  
  /**
   * <p>Returns whether the piece can move to the specified position using a repeatable move.
   * <p>This is intended to be overridden by subclasses for custom behavior.
   * <p>The position given by moveX and moveY is guaranteed to be within bounds.
   * <p>By default, this method returns VALID_STOP if it encounters an enemy,
   * INVALID_STOP if it encounters a friend, or VALID_CONTINUE if it encounters
   * an empty square.
   * 
   * @param delta The offset from the piece's current position
   * @param moveX The x position after making the move
   * @param moveY The y position after making the move
   * @param index The index of this move in the MoveSet
   * @param iteration How many times the move has been repeated thus far
   * @return <p>The action that should be taken:
   * <p>INVALID_STOP: if the move is invalid and iteration should stop,
   * <p>INVALID_CONTINUE: if the move is invalid but iteration should continue,
   * <p>VALID_CONTINUE: if the move is valid and iteration should continue,
   * <p>VALID_STOP: if the move is valid but iteration should stop.
   */
  protected RepeatableMoveResult canMoveRepeatable(IntVector2 delta, int moveX, int moveY, int index, int iteration) {
    ChessPiece pieceAtDestination = getBoard().getPiece(moveX, moveY);
    
    if (pieceAtDestination == null) {
      // the square is empty - we can keep going
      return RepeatableMoveResult.VALID_CONTINUE;
    } else if (pieceAtDestination.team == team) {
      // the square is occupied by one of our own pieces - we can't move there
      return RepeatableMoveResult.INVALID_STOP;
    } else {
      // the square is occupied by the enemy - we can capture the piece but can't go any further this turn
      return RepeatableMoveResult.VALID_STOP;
    }
  }
  
  /**
   * Return values for the canMoveRepeatable() method.
   */
  public enum RepeatableMoveResult {
    
    /**
     * Indicates that the move is invalid and iteration should stop.
     */
    INVALID_STOP,
    
    /**
     * Indicates that the move is invalid but iteration should continue.
     */
    INVALID_CONTINUE,
    
    /**
     * Indicates that the move is valid and iteration should continue.
     */
    VALID_CONTINUE,
    
    /**
     * Indicates that the move is valid but iteration should stop.
     */
    VALID_STOP;
  }
  
  /**
   * <p>This is called before all the moves from the standard MoveSets are added.
   * <p>It is intended to allow for more advanced custom behaviour, but is not used
   * by the standard chess pieces.
   */
  protected void addCustomMoves(List<IntVector2> moveList) {}
}
